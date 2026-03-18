package com.taidt9.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.taidt9")
public class OderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OderServiceApplication.class, args);
    }

}
