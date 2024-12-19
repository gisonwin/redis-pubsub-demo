package com.gisonwin.pubsub.service;

import com.gisonwin.pubsub.model.RedisMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2021/12/15 16:08
 */
@Service
public class Publisher {
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public Publisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public void pushMessage(String topic, RedisMessage message){
        redisTemplate.convertAndSend(topic,message);
    }
}
