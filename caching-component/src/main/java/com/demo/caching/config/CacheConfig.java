package com.demo.caching.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class CacheConfig {

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	@Override
	public String toString() {
		return "CacheConfig [name=" + name + ", expirySeconds=" + expirySeconds + "]";
	}

	public Long getExpirySeconds() {
		return expirySeconds;
	}

	public void setExpirySeconds(Long expirySeconds) {
		this.expirySeconds = expirySeconds;
	}

	String name;
	Long expirySeconds;


}
