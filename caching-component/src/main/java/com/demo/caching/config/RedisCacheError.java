package com.demo.caching.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;



public class RedisCacheError implements CacheErrorHandler {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Override
    public void handleCacheGetError(RuntimeException exception,
                                    Cache cache, Object key) {
        //Do something on Get Error i cache
    	LOG.error("Exception while Getting From Cache Server: {}",exception);
    			
    }
    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache,
                                    Object key, Object value) {
        //Do something on Put error in cache
    	LOG.error("Exception while Putting into Cache Server: {}",exception);
    }
    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache,
                                      Object key) {
        //Do something on error while Evict cache
    	LOG.error("Exception while Evicting From Cache Server: {}",exception);
    }
    @Override
    public void handleCacheClearError(RuntimeException exception,Cache cache){
        //Do something on error while clearing cache
    	LOG.error("Exception while Clearing Cache in Cache Server: {}",exception);
    }
}