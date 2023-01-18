package com.dima.meterscollector.model;

import com.dima.meterscollector.domain.MeterConfiguration;
import com.dima.meterscollector.repository.MeterConfigRepo;
import de.re.easymodbus.modbusclient.ModbusClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PollMeters {

    public void setMeterConfigActual(boolean meterConfigActual) {
        isMeterConfigActual = meterConfigActual;
    }

    private boolean isMeterConfigActual;
//    private MeterConfiguration[] meterConfigurations;

    private List<MeterConfiguration> meterConfigurations = new ArrayList<MeterConfiguration>();
    private final ModbusClient modbusClient = new ModbusClient();
    @Autowired
    private MeterConfigRepo meterConfigRepo;

    @Scheduled(fixedDelay = 5000)
    public void pollMeters(){
        if(!isMeterConfigActual){
            meterConfigurations = meterConfigRepo.findAll();
            setMeterConfigActual(true);
        }
        System.out.println("Polling start");
        for(MeterConfiguration meter : meterConfigurations){
            if(meter.isPollingEnable()){
                System.out.println("Reading: " + meter.getTitleEn());
                modbusClient.setipAddress(meter.getIpAddress());
                modbusClient.setUnitIdentifier(meter.getUnitId());
                if(meter.isAddrPEnable()){
                    try {
                        modbusClient.Connect();
                        System.out.println(ModbusClient.ConvertRegistersToFloat(
                                modbusClient.ReadHoldingRegisters(meter.getAddrP(), 2)));
                        modbusClient.Disconnect();
                    } catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        }
    }
}
