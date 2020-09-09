package br.com.spring.async.springbootasyncprovider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.async.springbootasyncprovider.model.User;
import br.com.spring.async.springbootasyncprovider.service.ProviderService;

@RestController
@RequestMapping("/provider")
public class ProviderController {

	@Autowired
	private ProviderService providerService;
	
	@GetMapping(value = "/{user}")
    public ResponseEntity<User> findUser(@PathVariable(value = "user") String user) throws InterruptedException {      
        return ResponseEntity.ok(providerService.findUser(user));
    }
}
