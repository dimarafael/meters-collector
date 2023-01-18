package com.dima.meterscollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MetersCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetersCollectorApplication.class, args);
    }

}
