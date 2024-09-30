package com.dima.meterscollector.controller;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dima.meterscollector.domain.MeterConfiguration;
import com.dima.meterscollector.model.MeterData;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

@Component
public class InfluxController {

    Logger logger = LoggerFactory.getLogger(InfluxController.class);

    @Value("${influxdb.token}")
    private String token;
    
    @Value("${influxdb.bucket}")
    private String bucket;
    
    @Value("${influxdb.org}")
    private String org;

    @Value("${influxdb.url}")
    private String influxdbUrl;

    public void sendMeterToInflux(MeterData meterData, MeterConfiguration meterConfiguration){
        InfluxDBClient client = InfluxDBClientFactory.create(influxdbUrl, token.toCharArray(), org, bucket);
        try{
            
            Point point = Point.measurement(meterConfiguration.getPosition())
            .addField("U1", meterData.getU1())
            .addField("U2", meterData.getU2())
            .addField("U3", meterData.getU3())
            .addField("U12", meterData.getU12())
            .addField("U23", meterData.getU23())
            .addField("U31", meterData.getU31())
            .addField("I1", meterData.getI1())
            .addField("I2", meterData.getI2())
            .addField("I3", meterData.getI3())
            .addField("P", meterData.getP())
            .addField("Q", meterData.getQ())
            .addField("S", meterData.getS())
            .addField("online", meterData.isOnline())
            .addTag("position", meterConfiguration.getPosition())
            .addTag("Title", meterConfiguration.getTitleHu())
            .time(Instant.now(), WritePrecision.NS);

            WriteApiBlocking writeApi = client.getWriteApiBlocking();
            writeApi.writePoint(point);
            
        }catch (Exception e){
            logger.error("Error sending data to influx. Exception: " + e);
        } finally {
            client.close();
        }
    }
}
