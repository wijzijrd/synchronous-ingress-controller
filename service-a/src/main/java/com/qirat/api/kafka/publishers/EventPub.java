package com.qirat.api.kafka.publishers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventPub {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.events.topic-name}")
    private String topicName;

    public void sendMessage(@NonNull final String message) {

        log.info("Publish event: {}", message);
        CompletableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topicName, message);

        future.whenCompleteAsync((stringStringSendResult, throwable) -> {
            if (throwable != null) {
                log.error("Unable to send message: {}; REASON: {}", message, throwable.getMessage());
            } else {
                log.info("Successfully wrote message: {}, to topic: {}", message, this.topicName);
            }
        });
    }
}
