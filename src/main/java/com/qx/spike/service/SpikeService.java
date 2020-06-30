package com.qx.spike.service;

import com.qx.spike.dto.SpikeExecution;
import com.qx.spike.entity.Spike;
import java.util.List;
import com.qx.spike.dto.Exposer;
import com.qx.spike.exception.RepeatSpikeException;
import com.qx.spike.exception.SpikeCloseException;

/**
 * Created by Qiang Xiao on 06/12/2020
 */
public interface SpikeService {
    /**
     * Query all spike records
     * @return
     */
    List<Spike> getSpikeList();

    /**
     * Query single spike record
     * @param spikeId
     * @return
     */
    Spike getSpikeById(long spikeId);

    /**
     * Output the url of the spike interface when spike is turned on, otherwise output the system time and spike time
     * @param spikeId
     * @return
     */
    Exposer exportSpikeUrl(long spikeId);

    /**
     * Execute the spike,it may fail or succeed, so throw the exception we allow
     * @param spikeId
     * @param userPhone
     * @param md5
     * @return
     * @throws SpikeCloseException
     * @throws RepeatSpikeException
     */
    SpikeExecution executeSpike(long spikeId, long userPhone, String md5) throws SpikeCloseException, RepeatSpikeException;
}
