package com.qirat.api.kafka.subscribers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qirat.api.redis.repo.EventCacheItem;
import com.qirat.api.redis.repo.EventCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableRedisRepositories
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventSub {
    private final EventCacheRepository cacheRepository;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "service-d-resp", groupId = "group-1")
    public void listen(final String message) throws JsonProcessingException {
        log.info("Received Message in group-1: {}" , message);

        EventCacheItem event = this.mapper.readValue(message, EventCacheItem.class);
        log.info("Push data to Redis: {}", event.getId());
        this.cacheRepository.save(event);
    }

}
