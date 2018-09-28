package com.demo.caching.config;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class CacheConstants {

	public static final String REFRESH_PATH="/refresh";
	public static final String REFRESH_CACHEMANAGER_PATH="/cache/cachemanager/refresh";
	public static final String CACHE_LOCK="~lock";
	public static final byte[] SET_EXPIRATION__KEYS_BY_PATTERN_LUA = new StringRedisSerializer().serialize(
			"local keys = redis.call('KEYS', ARGV[1]); local keysCount = table.getn(keys); if(keysCount > 0) then for _, key in ipairs(keys) do redis.call('expire', key,ARGV[2]); end; end; return keysCount;");
}
