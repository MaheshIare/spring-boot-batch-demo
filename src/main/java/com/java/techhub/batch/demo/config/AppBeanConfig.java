/**
 * 
 */
package com.java.techhub.batch.demo.config;

import java.time.Duration;
import java.util.Collections;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.java.techhub.batch.demo.interceptors.ApiRequestInterceptor;

/**
 * @author mahes
 *
 */
@Configuration
public class AppBeanConfig {

	@Bean("restTemplate")
	public RestTemplate restTemplate() {
		RestTemplateBuilder builder = new RestTemplateBuilder();
		builder.setReadTimeout(Duration.ofSeconds(60000));
		builder.setConnectTimeout(Duration.ofSeconds(60000));
		RestTemplate restTemplate = builder.build();
		restTemplate.setInterceptors(Collections.singletonList(new ApiRequestInterceptor()));
		return restTemplate;
	}
	
	@Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }
}
