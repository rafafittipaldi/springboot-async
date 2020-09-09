package br.com.spring.async.springbootasyncconsumer.controller;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.async.springbootasyncconsumer.model.User;
import br.com.spring.async.springbootasyncconsumer.service.AsyncConsumerService;

@RestController
@RequestMapping("/async-consumer")
public class AsyncConsumerController {
	
	private static final Logger logger = LoggerFactory.getLogger(AsyncConsumerController.class);

	@Autowired
	private AsyncConsumerService asyncConsumerService;
	
	@GetMapping
    public ResponseEntity<?> findUsers() throws Throwable {
		
		// Inicia o tempo
		long start = System.currentTimeMillis();
		
		Stream<User>            userStream;
		// Múltiplos Lookups
		CompletableFuture<User> userByLanguageFuture1 = asyncConsumerService.findUser("PivotalSoftware");
		CompletableFuture<User> userByLanguageFuture2 = asyncConsumerService.findUser("CloudFoundry");
		CompletableFuture<User> userByLanguageFuture3 = asyncConsumerService.findUser("Spring-Projects");
		CompletableFuture<User> userByLanguageFuture4 = asyncConsumerService.findUser("RameshMF");
		
		// Aguarda para que todos terminem
		CompletableFuture
				.allOf(userByLanguageFuture1, userByLanguageFuture2, userByLanguageFuture3, userByLanguageFuture4)
				.join();

		// Imprime resultado, incluindo o tempo decorrido
		logger.info("Tempo decorrido := " + (System.currentTimeMillis() - start));
		logger.info("--> " + userByLanguageFuture1.get());
		logger.info("--> " + userByLanguageFuture2.get());
		logger.info("--> " + userByLanguageFuture3.get());
		logger.info("--> " + userByLanguageFuture4.get());
		
		
        try {
        	//Transforma em Stream
			userStream = Stream.of(userByLanguageFuture1.get(), userByLanguageFuture2.get(),
					userByLanguageFuture3.get(), userByLanguageFuture4.get());
        } catch (Throwable e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error := Erro interno ao consultar dados.");
        }
        
        //Retorna em List/Json
        return ResponseEntity.ok(userStream.collect(Collectors.toList()));
    }
}
