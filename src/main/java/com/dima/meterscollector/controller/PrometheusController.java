package com.dima.meterscollector.controller;


import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;


@Component
public class PrometheusController {

    private double t(){
        return Math.random();
    }
    MeterRegistry meterRegistry;
    public PrometheusController(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
        Gauge g = Gauge.builder("my_test_meter", this::t).register(meterRegistry);
//        meterRegistry.config().meterFilter(MeterFilter.denyUnless(g.getId()));
    }

}
