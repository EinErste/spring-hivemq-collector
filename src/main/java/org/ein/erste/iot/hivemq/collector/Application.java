package org.ein.erste.iot.hivemq.collector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application {
    public static void main(String[] args) throws Exception {
        log.info("Application starting");
        SpringApplication.run(Application.class, args);
        log.info("Application started");
    }
}