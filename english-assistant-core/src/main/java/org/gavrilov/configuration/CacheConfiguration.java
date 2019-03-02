package org.gavrilov.configuration;

import org.gavrilov.domain.Category;
import org.gavrilov.domain.User;
import org.gavrilov.domain.World;
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
        List<Cache> caches = new ArrayList<Cache>();
        caches.add(new ConcurrentMapCache("users"));
        caches.add(new ConcurrentMapCache("worlds"));
        caches.add(new ConcurrentMapCache("categories"));
        caches.add(new ConcurrentMapCache(Category.class.getName()));
        caches.add(new ConcurrentMapCache(Category.class.getName() + ".worlds"));
        caches.add(new ConcurrentMapCache(User.class.getName()));
        caches.add(new ConcurrentMapCache(User.class.getName() + ".worlds"));
        caches.add(new ConcurrentMapCache(World.class.getName()));
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
