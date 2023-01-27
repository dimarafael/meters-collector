package com.dima.meterscollector.controller;


import com.dima.meterscollector.domain.MeterConfiguration;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;


@Component
public class PrometheusController {

    MeterRegistry meterRegistry;
    public PrometheusController(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
        meterRegistry.config()
                .meterFilter(MeterFilter.acceptNameStartsWith("meter"))
                .meterFilter(MeterFilter.deny());
    }

    public void registerMeters(List<MeterConfiguration> meterConfigurations, BiFunction<Long, Integer, Float> f){ //f -> id, (0-p, 1-q, 2-s)

        meterRegistry.clear();

        for(MeterConfiguration meter : meterConfigurations){
            if(meter.isPollingEnable()){
                if(meter.isAddrPEnable()){
                    Gauge.builder("meter"+meter.getId().toString()+"_p", ()->f.apply( meter.getId(),0)).description("Active power " + meter.getTitleEn()).register(meterRegistry);
                }
                if(meter.isAddrQEnable()){
                    Gauge.builder("meter"+meter.getId().toString()+"_q", ()->f.apply( meter.getId(),1)).description("Reactive power " + meter.getTitleEn()).register(meterRegistry);
                }
                if(meter.isAddrSEnable()){
                    Gauge.builder("meter"+meter.getId().toString()+"_s", ()->f.apply( meter.getId(),2)).description("Apparent power " + meter.getTitleEn()).register(meterRegistry);
                }
            }
        }

    }
}
