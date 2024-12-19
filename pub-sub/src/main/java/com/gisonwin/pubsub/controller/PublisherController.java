package com.gisonwin.pubsub.controller;

import com.gisonwin.pubsub.model.GoodsMessage;
import com.gisonwin.pubsub.model.UserMessage;
import com.gisonwin.pubsub.service.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2021/12/15 15:07
 */
@RestController
@RequestMapping
public class PublisherController {
    static final Logger logger = LoggerFactory.getLogger(PublisherController.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("pub/{id}")
    public String pubMsg(@PathVariable String id) {
        redisTemplate.convertAndSend("phone", "18910359863");
        redisTemplate.convertAndSend("phoneTest2", "13693032086");
        logger.info("Publisher send msg Topic...");
        return "success";
    }

    @Resource
    private Publisher publisher;
    @GetMapping("test")
    public void pushMessage(){
        UserMessage userMessage = new UserMessage();
        userMessage.setMsgId(UUID.randomUUID().toString().replace("-",""));
        userMessage.setUserId("1");
        userMessage.setUsername("admin");
        userMessage.setUsername("root");
        userMessage.setCreateStamp(System.currentTimeMillis());
        publisher.pushMessage("user",userMessage);
        GoodsMessage goodsMessage = new GoodsMessage();
        goodsMessage.setMsgId(UUID.randomUUID().toString().replace("-",""));
        goodsMessage.setGoodsType("苹果");
        goodsMessage.setNumber("十箱");
        goodsMessage.setCreateStamp(System.currentTimeMillis());
        publisher.pushMessage("goods",goodsMessage);
    }


}
