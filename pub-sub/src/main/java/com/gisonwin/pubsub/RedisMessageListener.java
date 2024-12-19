//package com.gisonwin.pubsub;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.listener.PatternTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
//
//import java.util.concurrent.CountDownLatch;
//
///**
// * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
// * @Description
// * @Date 2021/12/15 14:57
// */
//@Configuration
//public class RedisMessageListener {
//
//    @Bean
//    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter,
//                                                   MessageListenerAdapter listenerAdapterTest2){
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        //accept message key
//        container.addMessageListener(listenerAdapter,new PatternTopic("phone"));
//        container.addMessageListener(listenerAdapterTest2,new PatternTopic("phoneTest2"));
//        return container;
//    }
//    @Bean
//    public MessageListenerAdapter listenerAdapter(ReceiverRedisMessage receiver){
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }
//
//    @Bean
//    public MessageListenerAdapter listenerAdapterTest2(ReceiverRedisMessage receiver){
//        return new MessageListenerAdapter(receiver, "receiveMessage2");
//    }
//
//    @Bean
//    ReceiverRedisMessage receiver(CountDownLatch latch){
//        return new ReceiverRedisMessage(latch);
//    }
//    @Bean
//    public CountDownLatch latch(){
//        return new CountDownLatch(1);
//    }
//}
