//package com.gisonwin.pubsub;
//
//import com.gisonwin.pubsub.controller.PublisherController;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.concurrent.CountDownLatch;
//
///**
// * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
// * @Description
// * @Date 2021/12/15 15:04
// */
//public class ReceiverRedisMessage {
//    static final Logger logger = LoggerFactory.getLogger(ReceiverRedisMessage.class);
//
//    private CountDownLatch latch;
//
//    @Autowired
//    public ReceiverRedisMessage(CountDownLatch latch) {
//        this.latch = latch;
//    }
//
//    public void receiveMessage(String jsonMsg) {
//        logger.info("消费Redis消息列phone的数据。");
//        System.out.println(jsonMsg);
//        latch.countDown();
//    }
//
//    public void receiveMessage2(String jsonMsg) {
//        logger.info("消费Redis消息列phoneTest2的数据。");
//        System.out.println(jsonMsg);
//        latch.countDown();
//    }
//
//}
