package com.qirat.api.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qirat.api.kafka.publishers.EventPub;
import com.qirat.api.models.Input;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SvcDController {

    private final ObjectMapper mapper;

    public Mono<ResponseEntity<String>> sync(@RequestBody final Input input) throws JsonProcessingException {
        input.setMessage(
                Base64.getEncoder()
                        .encodeToString(
                                "I made it all the way!"
                                        .getBytes(StandardCharsets.UTF_8)
                        )
        );

        return Mono.just(ResponseEntity.ok(this.mapper.writeValueAsString(input)));
    }
}
