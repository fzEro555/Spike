package com.qx.spike.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.qx.spike.entity.Spike;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.qx.spike.utils.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;
import java.util.function.Function;

/**
 * Created by Qiang Xiao on 06/21/2020
 */
public class RedisDao {
    private final JedisPool jedisPool;

    public RedisDao(String ip, int port){
        jedisPool = new JedisPool(ip, port);
    }

    private RuntimeSchema<Spike> schema = RuntimeSchema.createFrom(Spike.class);

    public Spike getSpike(long spikeId){
        return getSpike(spikeId, null);
    }

    /**
     * Get spike from redis
     * @param spikeId
     * @param jedis
     * @return
     */
    public Spike getSpike(long spikeId, Jedis jedis){
        boolean hasJedis = jedis != null;
        try{
            if(!hasJedis){
                jedis = jedisPool.getResource();
            }
            try{
                String key = getSpikeRedisKey(spikeId);
                byte[] bytes = jedis.get(key.getBytes());
                if(bytes != null){
                    Spike spike = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, spike, schema);
                    return spike;
                }
            }finally {
                if(!hasJedis){
                    jedis.close();
                }
            }
        }catch (Exception e){

        }
        return null;
    }

    /**
     * Get from cache, if not, get from database. We use distributed lock here.
     * @param spikeId
     * @param getDataFromDb
     * @return
     */
    public Spike getOrPutSpike(long spikeId, Function<Long, Spike> getDataFromDb){
        String lockKey = "spike:locks:getSpike:" + spikeId;
        String lockRequestId = UUID.randomUUID().toString();
        Jedis jedis = jedisPool.getResource();

        try{
            // Loop until data is obtained
            while(true){
                Spike spike = getSpike(spikeId, jedis);
                if(spike != null){
                    return spike;
                }
                // Try to get lock
                // The lock expiration time is to prevent the sudden collapse of the program before it is unlocked,
                // which causes the problem that other threads cannot acquire the lock.
                // The expiration time is the longest time the business tolerates.
                boolean getLock = JedisUtils.tryGetDistributedLock(jedis, lockKey, lockRequestId, 1000);
                if(getLock){
                    // Get the lock, get the data from the database, then save redis
                    spike = getDataFromDb.apply(spikeId);
                    putSpike(spike, jedis);
                    return spike;
                }
                // Can't get the lock, sleep for a while, and then start again.
                // Sleep time needs to be considered, mainly depends on business processing speed.
                try{
                    Thread.sleep(100);
                }catch (InterruptedException ignored){

                }
            }
        }catch (Exception ignored){

        }finally{
            // No matter what happened, we have to unlock at last.
            JedisUtils.releaseDistributedLock(jedis,lockKey,lockRequestId);
            jedis.close();
        }
        return null;
    }

    private String getSpikeRedisKey(long spikeId){
        return "spike: " + spikeId;
    }

    public String putSpike(Spike spike){
        return putSpike(spike, null);
    }

    public String putSpike(Spike spike, Jedis jedis){
        boolean hasJedis = jedis != null;
        try{
            if(!hasJedis){
                jedis = jedisPool.getResource();
            }
            try{
                String key = getSpikeRedisKey(spike.getSpikeId());
                byte[] bytes = ProtostuffIOUtil.toByteArray(spike, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout = 60 * 60;
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
             }finally {
                if(!hasJedis){
                    jedis.close();
                }
            }
        }catch(Exception e){

        }
        return null;
    }
}
