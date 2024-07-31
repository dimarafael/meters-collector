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

    public void registerMeters(List<MeterConfiguration> meterConfigurations, BiFunction<Long, Integer, Float> f){ //f -> id, (0-p, 1-q, 2-s, 3-I1, 4-I2, 5-I3, 6-U1, 7-U2, 8-U3, 9-U12, 10-U23, 11-U31)

        meterRegistry.clear();

        for(MeterConfiguration meter : meterConfigurations){
            if(meter.isPollingEnable()){
                if(meter.isAddrPEnable()){
                    Gauge.builder("meter_"+meter.getPosition()+"_P", ()->f.apply( meter.getId(),0)).description("Active power " + meter.getTitleEn()).register(meterRegistry);
                }
                if(meter.isAddrQEnable()){
                    Gauge.builder("meter_"+meter.getPosition()+"_Q", ()->f.apply( meter.getId(),1)).description("Reactive power " + meter.getTitleEn()).register(meterRegistry);
                }
                if(meter.isAddrSEnable()){
                    Gauge.builder("meter_"+meter.getPosition()+"_S", ()->f.apply( meter.getId(),2)).description("Apparent power " + meter.getTitleEn()).register(meterRegistry);
                }
                if(meter.isAddrUIEnable()){
                    Gauge.builder("meter_"+meter.getPosition()+"_I1", ()->f.apply( meter.getId(),3)).description("Current I1" + meter.getTitleEn()).register(meterRegistry);
                    Gauge.builder("meter_"+meter.getPosition()+"_I2", ()->f.apply( meter.getId(),4)).description("Current I2" + meter.getTitleEn()).register(meterRegistry);
                    Gauge.builder("meter_"+meter.getPosition()+"_I3", ()->f.apply( meter.getId(),5)).description("Current I3" + meter.getTitleEn()).register(meterRegistry);
                    Gauge.builder("meter_"+meter.getPosition()+"_U1", ()->f.apply( meter.getId(),6)).description("Voltage U1" + meter.getTitleEn()).register(meterRegistry);
                    Gauge.builder("meter_"+meter.getPosition()+"_U2", ()->f.apply( meter.getId(),7)).description("Voltage U2" + meter.getTitleEn()).register(meterRegistry);
                    Gauge.builder("meter_"+meter.getPosition()+"_U3", ()->f.apply( meter.getId(),8)).description("Voltage U3" + meter.getTitleEn()).register(meterRegistry);
                    Gauge.builder("meter_"+meter.getPosition()+"_U12", ()->f.apply( meter.getId(),9)).description("Voltage U12" + meter.getTitleEn()).register(meterRegistry);
                    Gauge.builder("meter_"+meter.getPosition()+"_U23", ()->f.apply( meter.getId(),10)).description("Voltage U23" + meter.getTitleEn()).register(meterRegistry);
                    Gauge.builder("meter_"+meter.getPosition()+"_U31", ()->f.apply( meter.getId(),11)).description("Voltage U31" + meter.getTitleEn()).register(meterRegistry);
                }
            }
        }

    }
}
