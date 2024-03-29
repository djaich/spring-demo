package hr.sedamit.demo.config;

import hr.sedamit.demo.service.CacheNames;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<Cache> caches = new ArrayList<>();
        ConcurrentMapCache authorListCache = new ConcurrentMapCache(CacheNames.CACHE_AUTHOR_LIST);
        ConcurrentMapCache authorDetailsCache = new ConcurrentMapCache(CacheNames.CACHE_AUTHOR_DETAILS);

        caches.add(authorListCache);
        caches.add(authorDetailsCache);
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
