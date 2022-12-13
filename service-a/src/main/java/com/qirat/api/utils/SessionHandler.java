package com.qirat.api.utils;

import com.qirat.api.redis.repo.EventCacheItem;
import com.qirat.api.redis.repo.EventCacheRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SessionHandler {
    private final EventCacheRepository cacheRepository;

    public CompletableFuture<ResponseEntity<String>> response(@NonNull final UUID requestId) {

        return CompletableFuture.supplyAsync(() -> {
            while (true) {
                Optional<EventCacheItem> event = this.cacheRepository
                        .findById(requestId.toString());

                if (event.isPresent())
                    return ResponseEntity.ok(
                            event.get().getMessage()
                    );
            }
        });
    }
}
