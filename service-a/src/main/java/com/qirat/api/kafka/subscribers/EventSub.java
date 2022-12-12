package com.qirat.api.kafka.subscribers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventSub {

    @KafkaListener(topics = "service-d-resp", groupId = "qirat")
    public void listen(final String message) {
        // push to redis
        System.out.println("Received Message in group qirat: " + message);
    }

}
