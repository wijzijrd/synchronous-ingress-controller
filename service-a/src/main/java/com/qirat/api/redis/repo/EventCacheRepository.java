package com.qirat.api.redis.repo;

import org.springframework.data.repository.CrudRepository;

public interface EventCacheRepository extends CrudRepository<EventCacheItem, String> {
}
