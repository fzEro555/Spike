package com.qx.spike.utils;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * Created by Qiang Xiao on 06/20/2020
 */
public class JedisUtils {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * Try to get distributed lock
     * @param jedis Redis client
     * @param lockKey lock
     * @param requestId
     * @param expireTime
     * @return
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime){
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        return LOCK_SUCCESS.equals(result);
    }

    /**
     *
     * @param jedis Redis client
     * @param lockKey
     * @param requestId
     * @return
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId){
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        return result.equals(RELEASE_SUCCESS);
    }
}
