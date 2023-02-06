package com.dima.meterscollector.controller;

//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//import java.time.LocalTime;

@RestController
public class TestRestController {

//    @Autowired
//    private PrometheusController prometheusController;

    @GetMapping(value = "test")
    public String testPrometheus(){
//        prometheusController.registerMeters();
        return "Hello from test";
    }

//    @Scheduled(cron = "*/15 * * * * *")
//    public void testSchd(){
//        System.out.println(LocalTime.now());
//    }
}
