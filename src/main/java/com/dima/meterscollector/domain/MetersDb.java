package com.dima.meterscollector.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

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
//    private Date dateTime;
    private OffsetDateTime dateTime;

    @PrePersist
    private void onCreate(){
//        dateTime = new Date();
        dateTime = OffsetDateTime.now();
    }

    @JsonIgnore
    @ManyToOne
    private MeterConfiguration meterConfiguration;
}
