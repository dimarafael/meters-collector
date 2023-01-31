package com.dima.meterscollector.model;

import com.dima.meterscollector.controller.PrometheusController;
import com.dima.meterscollector.domain.MeterConfiguration;
import com.dima.meterscollector.repository.MeterConfigRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
//import de.re.easymodbus.modbusclient.ModbusClient;
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


    private List<MeterConfiguration> meterConfigurations = new ArrayList<>();
//    private final ModbusClient modbusClient = new ModbusClient();
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

    private float pollFloat(byte unitId, int addr, TCPMasterConnection con) throws ModbusException {
        req = new ReadMultipleRegistersRequest(addr,2);
        req.setUnitID(unitId);
        trans = new ModbusTCPTransaction(con);
        trans.setRequest(req);
        trans.execute();
        res = (ReadMultipleRegistersResponse) trans.getResponse();
        return getFloatFromRegisters(res.getRegisters());
    }

    @Autowired
    private MeterConfigRepo meterConfigRepo;

    @Autowired
    private PrometheusController prometheusController;

    @Autowired
    SimpMessagingTemplate template;

    @Scheduled(fixedDelay = 5000)
//    @Scheduled()
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
                meterData.setTitleEn(meter.getTitleEn());
                meterData.setTitleHu(meter.getTitleHu());
                meterData.setId(meter.getId());

                logger.debug("Modbus: Polling " + meter.getIpAddress() + " unitId=" + meter.getUnitId());
//                modbusClient.setipAddress(meter.getIpAddress());
//                modbusClient.setUnitIdentifier(meter.getUnitId());
                try {
                    con = new TCPMasterConnection(InetAddress.getByName(meter.getIpAddress()));
                    con.setPort(502);
                    con.connect();
//                    modbusClient.Connect();

                    if(meter.isAddrPEnable()){
                        try {
                            meterData.setP(pollFloat(meter.getUnitId(),meter.getAddrP(),con));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrP()
                                    + ". Exception: " + e);
                        }
                    }

                    if(meter.isAddrQEnable()){
                        try {
                            meterData.setQ(pollFloat(meter.getUnitId(),meter.getAddrQ(),con));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrQ()
                                    + ". Exception: " + e);
                        }
                    }

                    if(meter.isAddrSEnable()){
                        try {
                            meterData.setS(pollFloat(meter.getUnitId(),meter.getAddrS(),con));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrS()
                                    + ". Exception: " + e);
                        }
                    }

                    if(meter.isAddrEaEnable()){
                        try {
                            meterData.setEa(pollFloat(meter.getUnitId(),meter.getAddrEa(),con));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrEa()
                                    + ". Exception: " + e);
                        }
                    }

                    if(meter.isAddrErEnable()){
                        try {
                            meterData.setEr(pollFloat(meter.getUnitId(),meter.getAddrEr(),con));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrEr()
                                    + ". Exception: " + e);
                        }
                    }

                    if(meter.isAddrEgEnable()){
                        try {
                            meterData.setEg(pollFloat(meter.getUnitId(),meter.getAddrEg(),con));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrEg()
                                    + ". Exception: " + e);
                        }
                    }

                    if(meter.isAddrEsEnable()){
                        try {
                            meterData.setEs(pollFloat(meter.getUnitId(),meter.getAddrEs(),con));
                        } catch (Exception e){
                            logger.error("Modbus not response: " + meter.getIpAddress()
                                    + " unitId=" + meter.getUnitId()
                                    + " register=" + meter.getAddrEs()
                                    + ". Exception: " + e);
                        }
                    }
                    meterData.setOnline(true);
                } catch (Exception e){
                    logger.error("Modbus: " + meter.getIpAddress() + " ID=" + meter.getId() + " not reachable. Exception: " + e);
                    meterData.setOnline(false);
                }
                meterData.setPollTime(startTime.until(LocalTime.now(), ChronoUnit.MICROS));
                meterDataListCollecting.add(meterData); //Add data from this meter lo list
                try {
//                    modbusClient.Disconnect();
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
