package com.mmall.common;


import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Zhiwei on 2018/11/28.
 */
public class RedisPool {
    private static JedisPool pool;
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));
    private static Integer minIdLe = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));
    private static Boolean getTestOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));
    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));
    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdLe);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(getTestOnReturn);

        config.setBlockWhenExhausted(true);

        pool = new JedisPool(config,redisIp,redisPort,1000*2);
    }

    static{
        initPool();
    }
    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnResource(Jedis jedis){
            pool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis){
            pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args){
        Jedis jedis = pool.getResource();
        jedis.set("hzw","123");

        returnResource(jedis);

        pool.destroy();
        System.out.println("end");
    }
}
