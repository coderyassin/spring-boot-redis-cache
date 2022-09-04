package org.yascode.springbootrediscache.repository.cache.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.yascode.springbootrediscache.repository.cache.RedisRepository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl<H, HK, HV> implements RedisRepository<H, HK, HV> {

    private final RedisTemplate redisTemplate;

    @Override
    public void save(H key, HK hashKey, HV  value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public void saveAll(H key, Map<HK, HV> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public HV getByKey(H key, HK hashKey) {
        return (HV) redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public List<HV> getAllByKey(H key) {
        return redisTemplate.opsForHash().values(key);
    }

    @Override
    public void delete(H key, HK hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    @Override
    public boolean isEmpty(H key) {
        return redisTemplate.opsForHash().values(key).isEmpty();
    }
}
