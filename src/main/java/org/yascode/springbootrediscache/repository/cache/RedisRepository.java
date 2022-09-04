package org.yascode.springbootrediscache.repository.cache;

import java.util.List;
import java.util.Map;

public interface RedisRepository<H, HK, HV> {
    void save(H key, HK hashKey, HV  value);
    void saveAll(H key, Map<HK, HV> map);
    HV getByKey(H key, HK hashKey);
    List<HV> getAllByKey(H key);
    void delete(H key, HK hashKey);
    boolean isEmpty(H key);
}
