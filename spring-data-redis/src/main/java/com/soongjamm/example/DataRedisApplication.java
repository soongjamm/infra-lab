package com.soongjamm.example;

import com.soongjamm.example.redis.CouponCreatedEvent;
import com.soongjamm.example.redis.CouponUsedEvent;
import com.soongjamm.example.redis.Event;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.OffsetDateTime;

@SpringBootApplication
public class DataRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataRedisApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(RedisTemplate<String, Event> redisTemplate) {
        return args -> {
            ListOperations<String, Event> listOperations = redisTemplate.opsForList();
            listOperations.leftPush("test", new CouponCreatedEvent(1L, "비싼 쿠폰", "admin"));
            listOperations.leftPush("test", new CouponUsedEvent(1L, OffsetDateTime.now()));
            Event value1 = listOperations.rightPop("test");
            Event value2 = listOperations.rightPop("test");
            System.out.println(value1);
            System.out.println(value2);
        };
    }
}
