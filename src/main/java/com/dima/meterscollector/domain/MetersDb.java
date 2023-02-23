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
    private float ea; // Active energy
    private float ead; // Active energy delivered
    private float er; // Reactive energy
    private float eg; // Generated energy
    private float es; // Apparent energy
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private OffsetDateTime dateTime;

    @PrePersist
    private void onCreate(){
        dateTime = OffsetDateTime.now();
    }

    @JsonIgnore
    @ManyToOne
    private MeterConfiguration meterConfiguration;
}
