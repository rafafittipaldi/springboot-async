package br.com.spring.async.springbootasyncconsumer.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.spring.async.springbootasyncconsumer.model.User;

@Service
public class AsyncConsumerService {
	
	private static final Logger logger = LoggerFactory.getLogger(AsyncConsumerService.class);

	@Autowired
    private RestTemplate restTemplate;
	
	/**
	 * Consulta usuário de forma assíncrona
	 * @param user Usuário a ser pesquisado
	 * @return {@link CompletableFuture}
	 * @throws InterruptedException
	 */
	@Async("poolTask")
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
		logger.info("Procurando usuário := " + user);
		 
		User results = restTemplate.getForObject("http://localhost:8080/provider/{user}", User.class, user);
        
        return CompletableFuture.completedFuture(results);
    }
}
