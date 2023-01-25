package com.dima.meterscollector.model;

import com.dima.meterscollector.domain.MeterConfiguration;
import com.dima.meterscollector.repository.MeterConfigRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.re.easymodbus.modbusclient.ModbusClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class PollMeters {
    Logger logger = LoggerFactory.getLogger(PollMeters.class);

    public void setMeterConfigActual(boolean meterConfigActual) {
        isMeterConfigActual = meterConfigActual;
    }

    private boolean isMeterConfigActual;

    public List<MeterData> getMeterDataList() {
        return meterDataList;
    }

    private List<MeterData> meterDataList; // List for data to send for client, always have all data

    private final List<MeterData> meterDataListCollecting = new ArrayList<>(); // List for put data when polling all counters, can be empty at start of polling
    private List<MeterConfiguration> meterConfigurations = new ArrayList<>();
    private final ModbusClient modbusClient = new ModbusClient();
    @Autowired
    private MeterConfigRepo meterConfigRepo;
    @Autowired
    SimpMessagingTemplate template;

    @Scheduled(fixedDelay = 1000)
//    @Scheduled()
    public void pollMeters(){
        meterDataListCollecting.clear();
        if(!isMeterConfigActual){
            meterConfigurations = meterConfigRepo.findAll();
            setMeterConfigActual(true);
        }
        logger.debug("Modbus: Start polling");
        for(MeterConfiguration meter : meterConfigurations){
            if(meter.isPollingEnable()){
                LocalTime startTime = LocalTime.now();//For polling time calculation
                MeterData meterData = new MeterData(); //Object for collecting data from one meter
                meterData.setTitleEn(meter.getTitleEn());
                meterData.setTitleHu(meter.getTitleHu());
                meterData.setId(meter.getId());

                logger.debug("Modbus: Polling " + meter.getIpAddress() + " unitId=" + meter.getUnitId());
                modbusClient.setipAddress(meter.getIpAddress());
                modbusClient.setUnitIdentifier(meter.getUnitId());
                try {
                    modbusClient.Connect();

                    if(meter.isAddrPEnable()){
                        try {
                            meterData.setP(ModbusClient.ConvertRegistersToFloat(
                                    modbusClient.ReadHoldingRegisters(meter.getAddrP(), 2)));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                            + " register=" + meter.getAddrP());
                        }
                    }

                    if(meter.isAddrQEnable()){
                        try {
                            meterData.setQ(ModbusClient.ConvertRegistersToFloat(
                                    modbusClient.ReadHoldingRegisters(meter.getAddrQ(), 2)));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrQ()
                                    + ". Exception: " + e);
                        }
                    }

                    if(meter.isAddrSEnable()){
                        try {
                            meterData.setS(ModbusClient.ConvertRegistersToFloat(
                                    modbusClient.ReadHoldingRegisters(meter.getAddrS(), 2)));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrS()
                                    + ". Exception: " + e);
                        }
                    }

                    if(meter.isAddrEaEnable()){
                        try {
                            meterData.setEa(ModbusClient.ConvertRegistersToFloat(
                                    modbusClient.ReadHoldingRegisters(meter.getAddrEa(), 2)));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrEa()
                                    + ". Exception: " + e);
                        }
                    }

                    if(meter.isAddrErEnable()){
                        try {
                            meterData.setEr(ModbusClient.ConvertRegistersToFloat(
                                    modbusClient.ReadHoldingRegisters(meter.getAddrEr(), 2)));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrEr()
                                    + ". Exception: " + e);
                        }
                    }

                    if(meter.isAddrEgEnable()){
                        try {
                            meterData.setEg(ModbusClient.ConvertRegistersToFloat(
                                    modbusClient.ReadHoldingRegisters(meter.getAddrEg(), 2)));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrEg()
                                    + ". Exception: " + e);
                        }
                    }

                    if(meter.isAddrEsEnable()){
                        try {
                            meterData.setEs(ModbusClient.ConvertRegistersToFloat(
                                    modbusClient.ReadHoldingRegisters(meter.getAddrEs(), 2)));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrEs()
                                    + ". Exception: " + e);
                        }
                    }
                    meterData.setOnline(true);
                } catch (Exception e){
                    logger.error("Modbus: " + meter.getIpAddress() + " not reachable. Exception: " + e);
                    meterData.setOnline(false);
                }
                meterData.setPollTime(startTime.until(LocalTime.now(), ChronoUnit.MICROS));
                meterDataListCollecting.add(meterData); //Add data from this meter lo list
                try {
                    modbusClient.Disconnect();
                } catch (Exception e){
                    logger.error("Modbus disconnect from " + meter.getIpAddress() + ". Exception: " + e);
                }
            }
        }

        meterDataList = new ArrayList<>(meterDataListCollecting); //Put collected data to list for client
        template.convertAndSend("/topic/meters", getMeterDataList()); //Send data to websocket

        // For debug, print to log all meters data
        try {
            logger.debug(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(meterDataList));
        } catch (Exception e){
            logger.debug(String.valueOf(e));
        }

    }
}
