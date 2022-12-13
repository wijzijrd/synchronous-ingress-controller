package com.qirat.api.redis.repo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Getter @Setter
@RedisHash("EventCache")
public class EventCacheItem {
    private String id;
    private String message;
}
