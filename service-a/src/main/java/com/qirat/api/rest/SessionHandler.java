package com.qirat.api.rest;

import org.springframework.http.ResponseEntity;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SessionHandler {
    public CompletableFuture<ResponseEntity<Void>> response(UUID requestId) {
        return CompletableFuture.supplyAsync(() -> {
            // do lookup
            return ResponseEntity.ok().build();
        });
    }
}
