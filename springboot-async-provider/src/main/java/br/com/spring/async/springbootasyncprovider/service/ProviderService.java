package br.com.spring.async.springbootasyncprovider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.spring.async.springbootasyncprovider.model.User;

@Service
public class ProviderService {

	@Autowired
    private RestTemplate restTemplate;
	
    public User findUser(String user) throws InterruptedException {
        return restTemplate.getForObject("https://api.github.com/users/{user}", User.class, user);
    }
}
