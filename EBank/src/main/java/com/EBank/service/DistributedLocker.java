//package com.EBank.service;
//
///**
// * @author liuzhen
// * @version 1.0
// * @date 2019/7/8
// */
//import java.util.concurrent.TimeUnit;
//
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//
//public interface DistributedLocker {
//
//    RLock lock(String lockKey);
//
//    RLock lock(String lockKey, int timeout);
//
//    RLock lock(String lockKey, TimeUnit unit, int timeout);
//
//    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);
//
//    void unlock(String lockKey);
//
//    void unlock(RLock lock);
//
//    void setRedissonClient(RedissonClient redissonClient);
//}
