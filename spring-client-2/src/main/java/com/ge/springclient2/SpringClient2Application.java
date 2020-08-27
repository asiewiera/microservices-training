package com.ge.springclient2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringClient2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringClient2Application.class, args);
    }

}
