package br.com.spring.async.springbootasyncconsumer.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.async.springbootasyncconsumer.model.User;
import br.com.spring.async.springbootasyncconsumer.service.AsyncConsumerService;

@RestController
@RequestMapping("/async-consumer")
public class AsyncConsumerController {

	@Autowired
	private AsyncConsumerService asyncConsumerService;
	
	@GetMapping(value = "/{user}")
    public ResponseEntity<?> findUser(@PathVariable(value = "user") String user) throws Throwable {
		
		CompletableFuture<User> uerByLanguageFuture = asyncConsumerService.findUser(user);
		User                    objUser;
        try {
            objUser = uerByLanguageFuture.get();
        } catch (Throwable e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error := Erro interno ao consultar dados.");
        }
        return ResponseEntity.ok(objUser);
    }
}
