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

    private float i1;
    private float i2;
    private float i3;
    private float u1;
    private float u2;
    private float u3;
    private float u12;
    private float u23;
    private float u31;
}
