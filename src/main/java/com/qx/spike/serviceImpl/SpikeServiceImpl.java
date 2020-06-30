package com.qx.spike.serviceImpl;

import com.qx.spike.dao.SpikeDao;
import com.qx.spike.dao.cache.RedisDao;
import com.qx.spike.dto.SpikeExecution;
import com.qx.spike.dao.SuccessfulSpikeDao;
import com.qx.spike.entity.SuccessfulSpike;
import com.qx.spike.enums.SpikeStatusEnum;
import com.qx.spike.exception.RepeatSpikeException;
import com.qx.spike.exception.SpikeCloseException;
import com.qx.spike.exception.SpikeException;
import com.qx.spike.service.SpikeService;
import com.qx.spike.entity.Spike;
import com.qx.spike.dto.Exposer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Date;

/**
 * Created by Qiang Xiao on 06/12/2020
 */
@Service
public class SpikeServiceImpl implements SpikeService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public final String salt = "fzEro5!5055fz";

    // Inject dependency
    @Autowired
    private SpikeDao spikeDao;

    @Autowired
    private SuccessfulSpikeDao successfulSpikeDao;

    @Autowired
    private RedisDao redisDao;

    @Override
    public List<Spike> getSpikeList(){
        return spikeDao.queryAll(0, 4);
    }

    @Override
    public Spike getSpikeById(long spikeId){
        return redisDao.getOrPutSpike(spikeId, id -> spikeDao.queryById(spikeId));
    }

    @Override
    public Exposer exportSpikeUrl(long spikeId){
        Spike spike = getSpikeById(spikeId);
        // Cannot get the record of this spike item
        if(spike == null){
            return new Exposer(false, spikeId);
        }

        Date startTime = spike.getStartTime();
        Date endTime = spike.getEndTime();
        Date currentTime = new Date();

        // If the spike has not started or already ended
        if(startTime.getTime() > currentTime.getTime() || endTime.getTime() < currentTime.getTime()){
            return new Exposer(false, spikeId, currentTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        // The spike is on, return the id of spike and md5 used to encrypt
        String md5 = getMd5(spikeId);
        return new Exposer(true, md5, spikeId);
    }

    public String getMd5(long spikeId){
        String base = spikeId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * Spike Succeed, reduce the quantity. Spike fail, throw exception and rollback the transaction
     * @param spikeId
     * @param userPhone
     * @param md5
     * @return
     * @throws SpikeCloseException
     * @throws RepeatSpikeException
     */
    @Transactional
    /**
     * Advantages of using annotations to control transaction methods:
     * 1.The development team reached an agreement and clearly marked the programming style of the transaction method
     * 2.Ensure that the execution time of the transaction method is as short as possible,
     *   and do not intersperse with other network operations RPC/HTTP requests or stripped to the outside of the transaction method
     * 3.Not all methods require transactions, one modification operation and read-only operation does not need transaction control
     */
    @Override
    public SpikeExecution executeSpike(long spikeId, long userPhone, String md5) throws SpikeCloseException, RepeatSpikeException {
        if(md5 == null || !md5.equals(getMd5(spikeId))){
            throw new SpikeException("Spike data was re-wrote");
        }
        Date currentTime = new Date();
        try{
            // Reduce the quantity
            int updatedCount = spikeDao.reduceQuantity(spikeId, currentTime);
            // The quantity is not reduced, means that the spike is closed
            if(updatedCount <= 0){
                throw new SpikeCloseException("Spike closed");
            }
            else{
                // Check whether the user succeed spike
                int insertedCount = successfulSpikeDao.insertSuccessfulSpike(spikeId, userPhone);
                // Spike fail
                if(insertedCount <= 0){
                    throw new RepeatSpikeException("Spike repeated");
                }
                // Spike succeed
                else{
                    SuccessfulSpike successfulSpike = successfulSpikeDao.queryByIdWithSpike(spikeId, userPhone);
                    return new SpikeExecution(spikeId, SpikeStatusEnum.SUCCESS, successfulSpike);
                }
            }
        }catch(SpikeCloseException e1){
            throw e1;
        }catch(RepeatSpikeException e2){
            throw e2;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new SpikeException("Spike inner error" + e.getMessage());
        }
    }
}
