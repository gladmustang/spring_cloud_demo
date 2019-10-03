package com.example.client2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Home {
    @Autowired
    private  LoadBalancerClient loadBalancerClient;
    @Autowired
    private  RestTemplate restTemplate;
    @GetMapping("/")
    String home(){
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = this.loadBalancerClient.choose("CLient1");
        String host = instance.getHost();
        int port  = instance.getPort();

        String url  = String.format("http://%s:%s", host, port);
        String response = restTemplate.getForObject(url, String.class);

        return response + url;
    }
    @GetMapping("/rest2")
    String rest2() {
        String response = this.restTemplate.getForObject("http://client1/", String.class);
        return response;
    }
}
