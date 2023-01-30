package com.dima.meterscollector.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

//    @Autowired
//    private PrometheusController prometheusController;

    @GetMapping(value = "test")
    public String testPrometheus(){
//        prometheusController.registerMeters();
        return "Hello from test";
    }
}
