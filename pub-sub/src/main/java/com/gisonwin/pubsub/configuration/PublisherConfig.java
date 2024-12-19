package com.gisonwin.pubsub.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2021/12/15 15:43
 */
@Configuration
public class PublisherConfig {
    @Bean
    public RedisTemplate<String,Object> redisMessageTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
//        template.setDefaultSerializer();
        return template;
    }
}
