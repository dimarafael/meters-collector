package com.dima.meterscollector.model;

import lombok.Data;

@Data
public class MeterData {
    private String position;
    private String titleEn;
    private String titleHu;
    private float p; // Active power
    private float q; // Reactive power
    private float s; // Apparent power
    private float ea; // Active energy
    private float ead; // Active energy delivered
    private float er; // Reactive energy
    private float eg; // Generated energy
    private float es; //Apparent energy
    private long id; // Configuration id from db MeterConfiguration
    private float pollTime; //in microseconds
    private boolean online;
}
