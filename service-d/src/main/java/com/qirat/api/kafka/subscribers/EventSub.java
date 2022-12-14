package com.qirat.api.kafka.subscribers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qirat.api.kafka.publishers.EventPub;
import com.qirat.api.models.Input;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventSub {
    private final ObjectMapper mapper;
    private final EventPub pub;

    @KafkaListener(topics = "topic-c", groupId = "group-1")
    public void listen(final String message) throws JsonProcessingException {
        log.info("Received Message in group-1: {}" , message);

        Input input = this.mapper.readValue(message, Input.class);
        input.setMessage("I made it all the way!");

        this.pub.sendMessage(this.mapper.writeValueAsString(input));
    }

}
