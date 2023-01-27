package com.dima.meterscollector.controller;


import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.stereotype.Component;


@Component
public class PrometheusController {

    private double t(){
        return Math.random();
    }
    MeterRegistry meterRegistry;
    public PrometheusController(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
        meterRegistry.config()
                .meterFilter(MeterFilter.acceptNameStartsWith("meter"))
                .meterFilter(MeterFilter.deny());

        Gauge.builder("meter23_test_meter", this::t).description("Description").register(meterRegistry);
    }

}
