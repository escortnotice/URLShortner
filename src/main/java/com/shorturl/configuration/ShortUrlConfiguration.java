package com.shorturl.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ShortUrlConfiguration {

	@Bean
	public ObjectMapper getJacksonObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper;
	}
}
