package br.com.spring.async.springbootasyncprovider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProviderConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
