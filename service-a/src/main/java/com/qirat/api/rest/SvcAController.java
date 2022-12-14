package com.qirat.api.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qirat.api.kafka.publishers.EventPub;
import com.qirat.api.models.Input;
import com.qirat.api.utils.SessionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@RestController
@RequestMapping("api")
@EnableRedisRepositories
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SvcAController {

    private final EventPub pub;
    private final ObjectMapper mapper;
    private final WebClient webClient;
    private final SessionHandler sessionHandler;

    @PostMapping(path = "/sync")
    public Mono<ResponseEntity<String>> sync(@RequestBody final Input input) {
        Instant start = Instant.now();

        try {
            return this.webClient
                    .post()
                    .uri("/api/sync")
                    .bodyValue(input)
                    .retrieve()
                    .toEntity(String.class);
        } finally {
            log.info("Synchronous call took: {} ms", (Instant.now().compareTo(start)/1000));
        }

    }

    @PostMapping(path = "/async")
    public Mono<ResponseEntity<String>> async(
            @RequestBody final Input input) throws JsonProcessingException {

        Instant start = Instant.now();
        log.info("Publish event with id {}", input.getId());
        this.pub.sendMessage(this.mapper.writeValueAsString(input));

        // wait for published response event from service-d
        return Mono.fromFuture(this.sessionHandler.response(input.getId()))
                .doOnSuccess(stringResponseEntity ->
                        log.info("Synchronous call took: {} ms", (Instant.now().compareTo(start)/1000)));
    }
}
