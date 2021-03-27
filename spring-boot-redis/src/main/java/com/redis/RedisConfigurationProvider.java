package com.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfigurationProvider {

	@Bean
	public LettuceConnectionFactory lettuceConnectionFactory() {
		RedisClusterConfiguration clusterConfiguration = new  RedisClusterConfiguration();
		clusterConfiguration.clusterNode("10.32.142.13", 5000);
		clusterConfiguration.setPassword("reliance123".toCharArray());
		LettuceClientConfiguration lettuceClientConfig = LettuceClientConfiguration.defaultConfiguration();
		return new LettuceConnectionFactory(clusterConfiguration,lettuceClientConfig);
	}
	
	@Bean
	public RedisTemplate<String, String> redisTemplate(){
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(lettuceConnectionFactory());
		return template;
	}
}
