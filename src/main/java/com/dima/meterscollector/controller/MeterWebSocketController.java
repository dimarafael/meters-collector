package com.dima.meterscollector.controller;

import com.dima.meterscollector.model.MeterData;
import com.dima.meterscollector.model.PollMeters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MeterWebSocketController {

    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    PollMeters pollMeters;

    @GetMapping("/api/getdatatosocket")
    public ResponseEntity<Void> getData(){
        if(pollMeters.getMeterDataList() != null){
            template.convertAndSend("/topic/meters", pollMeters.getMeterDataList());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SendTo("/topic/meters")
    public List<MeterData> sendMetersData(List<MeterData> data) {
        return data;
    }
}
