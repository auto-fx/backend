package com.autofx.autofxbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthenticationAutofxApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationAutofxApplication.class, args);
    }

}
