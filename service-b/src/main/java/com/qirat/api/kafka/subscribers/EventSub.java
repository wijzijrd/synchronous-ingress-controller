package com.qirat.api.kafka.subscribers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventSub {
    private final ObjectMapper mapper;

    @KafkaListener(topics = "topic-a", groupId = "group-1")
    public void listen(final String message) throws JsonProcessingException {
        log.info("Received Message in group-1: {}" , message);

    }

}
