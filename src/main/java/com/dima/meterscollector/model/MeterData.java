package com.dima.meterscollector.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MeterData {
    private String titleEn;
    private String titleHu;
    private float p;
    private float q;
    private float s;
    private float ea;
    private float er;
    private float eg;
    private float es;
    private long id;
}
