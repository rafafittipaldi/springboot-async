package br.com.spring.async.springbootasyncconsumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

/**
 * Calsse de configuração
 * Habilita o Async
 * @author Rafael Fittipaldi
 *
 */
@Configuration
@EnableAsync
public class AsyncConsumerConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	/**
	 * Cria o Bean de executor de tarefas com prefico "Async-"
	 * @return {@link TaskExecutor}
	 */
	@Bean("poolTask")
	public TaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize                    (20);
		executor.setMaxPoolSize                     (1000);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix                ("Async-");
		return executor;
	}
}
