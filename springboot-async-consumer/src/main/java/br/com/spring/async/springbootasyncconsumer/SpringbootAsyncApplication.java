package br.com.spring.async.springbootasyncconsumer;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.spring.async.springbootasyncconsumer.controller.AsyncConsumerController;

@SpringBootApplication
public class SpringbootAsyncApplication implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(SpringbootAsyncApplication.class);

	@Autowired
	private AsyncConsumerController asyncConsumerController;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAsyncApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Inicia o tempo
		long start = System.currentTimeMillis();
		
		try {
			// Multiplos Lookups
			CompletableFuture<?> git1 = CompletableFuture.completedFuture(asyncConsumerController.findUser("PivotalSoftware"));
			CompletableFuture<?> git2 = CompletableFuture.completedFuture(asyncConsumerController.findUser("CloudFoundry"));
			CompletableFuture<?> git3 = CompletableFuture.completedFuture(asyncConsumerController.findUser("Spring-Projects"));
			CompletableFuture<?> git4 = CompletableFuture.completedFuture(asyncConsumerController.findUser("RameshMF"));
			
			// Aguarda para que todos terminem
			CompletableFuture.allOf(git1, git2, git3, git4).join();

			// Imprime resultado, incluindo o tempo decorrido
			logger.info("Tempo decorrido := " + (System.currentTimeMillis() - start));
			logger.info("--> " + git1.get());
			logger.info("--> " + git2.get());
			logger.info("--> " + git3.get());
			logger.info("--> " + git4.get());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
