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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    public Mono<ResponseEntity<Void>> sync(@RequestBody final Input input) {
        return this.webClient
                .get()
                .uri("/api")
                .retrieve()
                .toBodilessEntity();

    }

    public Mono<ResponseEntity<String>> async(
            @RequestBody final Input input) throws JsonProcessingException {

        log.info("Publish event with id {}", input.getRequestId());
        this.pub.sendMessage(this.mapper.writeValueAsString(input));

        // wait for published response event from service-d
        return Mono.fromFuture(
                this.sessionHandler.response(input.getRequestId())
        );
    }




















}
