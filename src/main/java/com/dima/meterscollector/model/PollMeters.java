package com.dima.meterscollector.model;

import com.dima.meterscollector.controller.PrometheusController;
import com.dima.meterscollector.domain.MeterConfiguration;
import com.dima.meterscollector.repository.MeterConfigRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersResponse;
import com.ghgande.j2mod.modbus.net.TCPMasterConnection;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.util.ModbusUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class PollMeters {

    public float getValues(Long id, int req){ //req: 0-p, 1-q, 2-s
        float res = 0;
        for(MeterData data : meterDataList){
            if(data.getId() == id){
                res = switch (req) {
                    case 0 -> data.getP();
                    case 1 -> data.getQ();
                    case 2 -> data.getS();
                    default -> 0;
                };
            }
        }
        return res;
    }

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


    public List<MeterConfiguration> getMeterConfigurations() {
        return meterConfigurations;
    }

    private List<MeterConfiguration> meterConfigurations = new ArrayList<>();
    protected static TCPMasterConnection con = null;
    protected static ModbusTCPTransaction trans = null;
    protected static ReadMultipleRegistersRequest req = null; //the request
    protected static ReadMultipleRegistersResponse res = null; //the response
    private float getFloatFromRegisters(Register[] registers){
        if(registers.length<2) throw new NullPointerException();
        byte[] arr = new byte[4];
        arr[0] = res.getRegister(1).toBytes()[0];
        arr[1] = res.getRegister(1).toBytes()[1];
        arr[2] = res.getRegister(0).toBytes()[0];
        arr[3] = res.getRegister(0).toBytes()[1];
        return ModbusUtil.registersToFloat(arr);
    }

    private float getSwappedFloatFromRegisters(Register[] registers){
        if(registers.length<2) throw new NullPointerException();
        byte[] arr = new byte[4];
        arr[0] = res.getRegister(0).toBytes()[0];
        arr[1] = res.getRegister(0).toBytes()[1];
        arr[2] = res.getRegister(1).toBytes()[0];
        arr[3] = res.getRegister(1).toBytes()[1];
        return ModbusUtil.registersToFloat(arr);
    }

    private float pollFloat(byte unitId, int addr, TCPMasterConnection con, boolean dataInKilo) throws ModbusException {
        req = new ReadMultipleRegistersRequest(addr,2);
        req.setUnitID(unitId);
        trans = new ModbusTCPTransaction(con);
        trans.setRequest(req);
        trans.execute();
        res = (ReadMultipleRegistersResponse) trans.getResponse();
        return dataInKilo ? getSwappedFloatFromRegisters(res.getRegisters()) : getFloatFromRegisters(res.getRegisters())/1000;
    }

    @Autowired
    private MeterConfigRepo meterConfigRepo;

    @Autowired
    private PrometheusController prometheusController;

    @Autowired
    SimpMessagingTemplate template;

    @Scheduled(fixedDelay = 5000)
    public void pollMeters(){
        meterDataListCollecting.clear();
        if(!isMeterConfigActual){
            meterConfigurations = meterConfigRepo.findAll();
            prometheusController.registerMeters(meterConfigurations, this::getValues);//register meters for prometheus metrics
            setMeterConfigActual(true);
        }
        logger.debug("Modbus: Start polling");
        for(MeterConfiguration meter : meterConfigurations){
            if(meter.isPollingEnable()){
                LocalTime startTime = LocalTime.now();//For polling time calculation
                MeterData meterData = new MeterData(); //Object for collecting data from one meter
                meterData.setPosition(meter.getPosition());
                meterData.setTitleEn(meter.getTitleEn());
                meterData.setTitleHu(meter.getTitleHu());
                meterData.setId(meter.getId());

                logger.debug("Modbus: Polling " + meter.getIpAddress() + " unitId=" + meter.getUnitId());
                try {
                    con = new TCPMasterConnection(InetAddress.getByName(meter.getIpAddress()));
                    con.setPort(502);
                    con.connect();
                    meterData.setOnline(true);

                    if(meter.isAddrPEnable()){
                        try {
                            meterData.setP(pollFloat(meter.getUnitId(),meter.getAddrP(),con, meter.isDataInKilo()));
                        } catch (Exception e1){
                            try { // second poll if error polling
                                meterData.setP(pollFloat(meter.getUnitId(),meter.getAddrP(),con, meter.isDataInKilo()));
                            } catch (Exception e) {
                                logger.error("Modbus not response: " + meter.getIpAddress()
                                        + " unitId=" + meter.getUnitId()
                                        + " register=" + meter.getAddrP()
                                        + ". Exception: " + e);
                                meterData.setOnline(false);
                                meterData.setP(0);
                            }
                        }
                    }

                    if(meter.isAddrQEnable()){
                        try {
                            meterData.setQ(pollFloat(meter.getUnitId(),meter.getAddrQ(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrQ()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            meterData.setQ(0);
                        }
                    }

                    if(meter.isAddrSEnable()){
                        try {
                            meterData.setS(pollFloat(meter.getUnitId(),meter.getAddrS(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrS()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            meterData.setS(0);
                        }
                    }

                    if(meter.isAddrEaEnable()){
                        try {
                            meterData.setEa(pollFloat(meter.getUnitId(),meter.getAddrEa(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrEa()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            MeterData previousData = meterDataList.stream()
                                    .filter(data -> meter.getId().equals(data.getId()))
                                    .findAny().orElse(null);
                            if(previousData != null) meterData.setEa(previousData.getEa());
                        }
                    }

                    if(meter.isAddrEadEnable()){
                        try {
                            meterData.setEad(pollFloat(meter.getUnitId(),meter.getAddrEad(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrEad()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            MeterData previousData = meterDataList.stream()
                                    .filter(data -> meter.getId().equals(data.getId()))
                                    .findAny().orElse(null);
                            if(previousData != null) meterData.setEad(previousData.getEad());
                        }
                    }

                    if(meter.isAddrErEnable()){
                        try {
                            meterData.setEr(pollFloat(meter.getUnitId(),meter.getAddrEr(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrEr()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            MeterData previousData = meterDataList.stream()
                                    .filter(data -> meter.getId().equals(data.getId()))
                                    .findAny().orElse(null);
                            if(previousData != null) meterData.setEr(previousData.getEr());
                        }
                    }

                    if(meter.isAddrEgEnable()){
                        try {
                            meterData.setEg(pollFloat(meter.getUnitId(),meter.getAddrEg(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrEg()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            MeterData previousData = meterDataList.stream()
                                    .filter(data -> meter.getId().equals(data.getId()))
                                    .findAny().orElse(null);
                            if(previousData != null) meterData.setEg(previousData.getEg());
                        }
                    }

                    if(meter.isAddrEsEnable()){
                        try {
                            meterData.setEs(pollFloat(meter.getUnitId(),meter.getAddrEs(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrEs()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            MeterData previousData = meterDataList.stream()
                                    .filter(data -> meter.getId().equals(data.getId()))
                                    .findAny().orElse(null);
                            if(previousData != null) meterData.setEs(previousData.getEs());
                        }
                    }

                    if(meter.isAddrUIEnable()){
                        //I1
                        try {
                            meterData.setI1(pollFloat(meter.getUnitId(),meter.getAddrI1(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrI1()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            meterData.setI1(0);
                        }

                        //I2
                        try {
                            meterData.setI2(pollFloat(meter.getUnitId(),meter.getAddrI2(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrI2()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            meterData.setI2(0);
                        }

                        //I3
                        try {
                            meterData.setI3(pollFloat(meter.getUnitId(),meter.getAddrI3(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrI3()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            meterData.setI3(0);
                        }

                        //U1
                        try {
                            meterData.setU1(pollFloat(meter.getUnitId(),meter.getAddrU1(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrU1()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            meterData.setU1(0);
                        }

                        //U2
                        try {
                            meterData.setU2(pollFloat(meter.getUnitId(),meter.getAddrU2(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrU2()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            meterData.setU2(0);
                        }

                        //U3
                        try {
                            meterData.setU3(pollFloat(meter.getUnitId(),meter.getAddrU3(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrU3()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            meterData.setU3(0);
                        }

                        //U12
                        try {
                            meterData.setU12(pollFloat(meter.getUnitId(),meter.getAddrU12(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrU12()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            meterData.setU12(0);
                        }

                        //U23
                        try {
                            meterData.setU23(pollFloat(meter.getUnitId(),meter.getAddrU23(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrU23()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            meterData.setU23(0);
                        }

                        //U31
                        try {
                            meterData.setU31(pollFloat(meter.getUnitId(),meter.getAddrU31(),con, meter.isDataInKilo()));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrU31()
                                    + ". Exception: " + e);
                            meterData.setOnline(false);
                            meterData.setU31(0);
                        }
                    }

                } catch (Exception e){
                    logger.error("Modbus: " + meter.getIpAddress() + " meter ID=" + meter.getId() + " not reachable. Exception: " + e);

                    MeterData previousData = meterDataList.stream()
                            .filter(data -> meter.getId().equals(data.getId()))
                            .findAny().orElse(null);
                    if(previousData != null) meterData = previousData;
                    meterData.setP(0);
                    meterData.setQ(0);
                    meterData.setS(0);
                    meterData.setOnline(false);

                    meterData.setI1(0);
                    meterData.setI2(0);
                    meterData.setI3(0);
                    meterData.setU1(0);
                    meterData.setU2(0);
                    meterData.setU3(0);
                    meterData.setU12(0);
                    meterData.setU23(0);
                    meterData.setU31(0);
                }
                meterData.setPollTime(startTime.until(LocalTime.now(), ChronoUnit.MICROS));
                meterDataListCollecting.add(meterData); //Add data from this meter lo list
                try {
                    con.close();
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
