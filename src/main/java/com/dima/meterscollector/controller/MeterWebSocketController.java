package com.dima.meterscollector.controller;

import com.dima.meterscollector.model.MeterData;
import com.dima.meterscollector.model.PollMeters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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

    @GetMapping("/api/getdata")
    public ResponseEntity<Void> getData(){
//        template.convertAndSend("/topic/meters", "Hello!");
        template.convertAndSend("/topic/meters", pollMeters.getMeterDataList());
//        System.out.println(pollMeters.getMeterDataList());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void sendData(){
        template.convertAndSend("/topic/meters", pollMeters.getMeterDataList());
    }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload String textMessage) {
        // receive message from client
    }

    @SendTo("/topic/meters")
    public List<MeterData> hello(List<MeterData> data) {
        return data;
    }
}
