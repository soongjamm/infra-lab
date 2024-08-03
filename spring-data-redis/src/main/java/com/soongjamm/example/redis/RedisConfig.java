package com.soongjamm.example.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Event> redisTemplate(RedisConnectionFactory connectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS); // java bean 이 없을 경우, 실패하는 feature 무시
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // timestamp(1722695497.068807000) 대신 iso-8601 (2024-08-03T23:35:12.271669+09:00) 로 직렬화
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE); // default timezone(UTC) 로 변환하는 기능을 off
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); // 모든 visibility 에 대해 deserialize (getter 가 없어도)
        objectMapper.registerModule(new JavaTimeModule());

        RedisTemplate<String, Event> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Serializer 를 지정하지 않으면 JDK 방식 직렬화를 해서 기대한대로 값이 저장되지 않는다.
        // eg. "\xac\xed\x00\x05t\x00\x04abcd"
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Event> valueSerializer = new Jackson2JsonRedisSerializer<>(Event.class);
        valueSerializer.setObjectMapper(objectMapper);

        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        return template;
    }
}
