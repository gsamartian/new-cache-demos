package com.demo.caching.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisCacheConfig implements CachingConfigurer {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Value("${spring.redis.sentinel.master}")
	String redisSentinelMaster;

	@Value("${spring.redis.sentinel.nodes}")
	String redisSentinelNodes;

	@Autowired
	AppConfig appConfig;

	@Bean
	public List<RedisNode> createSentinels() {
		LOG.info("Entering createSentinels..");
		List<RedisNode> nodes = new ArrayList<RedisNode>();
		for (String node : StringUtils.commaDelimitedListToStringArray(redisSentinelNodes)) {
			try {
				String[] parts = StringUtils.split(node, ":");
				Assert.state(parts.length == 2, "Must be defined as 'host:port'");
				nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
			} catch (RuntimeException ex) {
				throw new IllegalStateException("Invalid redis sentinel " + "property '" + node + "'", ex);
			}
		}
		LOG.info("Leaving createSentinels..");
		return nodes;
	}

	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		LOG.info("Entering cacheConfiguration..");
		long defaultCacheExpiryTime = appConfig.getDefaultCacheExpiryTime();
		if (defaultCacheExpiryTime <= 0) {
			defaultCacheExpiryTime = 300;
		}
		LOG.debug("Entering cacheConfiguration..");
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(defaultCacheExpiryTime)).disableCachingNullValues()
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer()))
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer(jacksonJsonRedisJsonSerializer()));
		LOG.info("Leaving cacheConfiguration..");
		return cacheConfig;
	}

	@Bean
	public RedisCacheManager cacheManager() {

		LOG.info("Entering cacheManager..");
		if (null != appConfig) {
			List<CacheConfig> cacheConfigList = appConfig.getCacheConfig();
			if (null != cacheConfigList) {
				Map<String, Long> cacheConfigMap = cacheConfigList.stream().filter(
						cacheConfig -> cacheConfig.getExpirySeconds() != null && cacheConfig.getExpirySeconds() != 0)
						.collect(Collectors.toMap(x -> x.getName(), x -> x.getExpirySeconds()));

				Map<String, RedisCacheConfiguration> redisConfigMap = new HashMap<>();

				cacheConfigMap.forEach((k, v) -> {
					LOG.info("cacheName:{},cacheExpirySeconds:{}", k, v);
					RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
							.entryTtl(Duration.ofSeconds(v)).disableCachingNullValues()
							.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer()))
							.serializeValuesWith(
									RedisSerializationContext.SerializationPair.fromSerializer(jacksonJsonRedisJsonSerializer()));
					redisConfigMap.put(k, cacheConfig);//
				});

				RedisCacheManager rcm = RedisCacheManager.builder(jedisConnectionFactory())
						.withInitialCacheConfigurations(redisConfigMap).transactionAware().build();
				LOG.info("Leaving cacheManager..");
				return rcm;
			} else {
				RedisCacheManager rcm = RedisCacheManager.builder(jedisConnectionFactory())
						.cacheDefaults(cacheConfiguration()).transactionAware().build();
				return rcm;
			}
		} else {
			RedisCacheManager rcm = RedisCacheManager.builder(jedisConnectionFactory())
					.cacheDefaults(cacheConfiguration()).transactionAware().build();
			return rcm;
		}
	}

	@Bean
	public RedisConnectionFactory jedisConnectionFactory() {
		LOG.debug("Redis Sentinel Nodes:{}", redisSentinelNodes);
		List<RedisNode> nodes = createSentinels();
		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration().master(redisSentinelMaster);
		sentinelConfig.setSentinels(nodes);
		JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory(sentinelConfig);
		return jedisConnFactory;
	}

	@Bean
	public StringRedisSerializer stringRedisSerializer() {
		LOG.debug("Entering stringRedisSerializer..");
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		return stringRedisSerializer;
	}

	@Bean
	public Jackson2JsonRedisSerializer<Object> jacksonJsonRedisJsonSerializer() {
		LOG.debug("Entering jacksonJsonRedisJsonSerializer..");
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		LOG.debug("Leaving jacksonJsonRedisJsonSerializer..");
		return jackson2JsonRedisSerializer;
	}

	@Override
	public CacheResolver cacheResolver() {
		return null;
	}

	@Override
	public KeyGenerator keyGenerator() {
		return null;
	}

	@Override
	public CacheErrorHandler errorHandler() {
		return new RedisCacheError();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		LOG.debug("Entering RedisTemplate...");

		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setConnectionFactory(jedisConnectionFactory());
		LOG.debug("Leaving RedisTemplate.");

		return template;
	}
}
