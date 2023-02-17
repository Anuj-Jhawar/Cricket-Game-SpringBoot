package org.service.springapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.controller")
public class SpringApplicationCode {
    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationCode.class,args);
    }
}
