package com.core.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Collections;
import java.util.List;

@Component
public class RedisUtil {
    @Value("${redis.uri}")
    private String url;
    @Value("${redis.port}")
    private int port;
    private JedisPooled jedisPooled;
    @PostConstruct
    private void init(){
        HostAndPort hostAndPort = new HostAndPort(url,port);

        jedisPooled = new JedisPooled(hostAndPort, DefaultJedisClientConfig.builder().socketTimeoutMillis(5000).connectionTimeoutMillis(5000).build());
    }


    public void set(String key , String value){
        jedisPooled.set(key, value);
    }
    public String get(String key){
        return jedisPooled.get(key);
    }
    public void setWithExpiration(String key,Long expireTime , String value){
        jedisPooled.setex(key,expireTime,value);
    }
    public String getOrCreate(String key , String value){
        var result = jedisPooled.get(key);
        if (result!=null){
            return result;
        }else{
            jedisPooled.set(key,value);
            return value;
        }
    }
    public void addToList(String key, String value) {
        try {
            jedisPooled.rpush(key, value);
        } catch (JedisException e) {
            // 记录日志或抛出异常
            e.printStackTrace();
        }
    }
    public List<String> getAllFromList(String key) {
        try {
            // 获取列表中的所有元素
            return jedisPooled.lrange(key, 0, -1);
        } catch (JedisException e) {
            // 记录日志或抛出异常
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public void addToListWithExpiration(String key, String value, long expireTimeInSeconds) {
        try {
            jedisPooled.rpush(key, value); // 向列表添加元素
            jedisPooled.expire(key, expireTimeInSeconds); // 设置过期时间，单位是秒
        } catch (JedisException e) {
            e.printStackTrace();
        }
    }
    public void delete(String key){
        jedisPooled.del(key);
    }

}
