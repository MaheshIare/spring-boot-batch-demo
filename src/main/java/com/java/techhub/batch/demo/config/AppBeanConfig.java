/**
 * 
 */
package com.java.techhub.batch.demo.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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
		return builder.build();
	}
}
