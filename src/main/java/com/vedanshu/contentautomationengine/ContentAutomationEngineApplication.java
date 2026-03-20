package com.vedanshu.contentautomationengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ContentAutomationEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentAutomationEngineApplication.class, args);
    }
}
