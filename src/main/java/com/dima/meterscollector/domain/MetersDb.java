package com.dima.meterscollector.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class MetersDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float ea;
    private float er;
    private float eg;
    private float es;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateTime;

    @PrePersist
    private void onCreate(){
        dateTime = new Date();
    }

    @ManyToOne
    MeterConfiguration meterConfiguration;
}
