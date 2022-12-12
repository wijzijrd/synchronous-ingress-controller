package com.qirat.api.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qirat.api.kafka.publishers.EventPub;
import com.qirat.api.rest.models.Input;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SvcAController {

    private final EventPub pub;
    private final ObjectMapper mapper;
    private final WebClient webClient;
    private final SessionHandler sessionHandler;

    public Mono<ResponseEntity<Void>> sync(@RequestBody final Input input) {
        return this.webClient
                .get()
                .uri("http://localhost:8081/api")
                .retrieve()
                .toBodilessEntity();

    }

    public Mono<ResponseEntity<Void>> async(@RequestBody final Input input) throws JsonProcessingException {
        this.pub.sendMessage(this.mapper.writeValueAsString(input));

        return Mono.fromFuture(this.sessionHandler.response(input.getRequestId()));
    }
}
