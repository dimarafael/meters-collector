package com.dima.meterscollector.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeterConfiguration {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;
    private String titleEn;
    private String titleHu;
    private boolean pollingEnable;
    private String ipAddress;
    private byte unitId;
    private int addrP; // Active power
    private boolean addrPEnable;
    private int addrQ; // Reactive power
    private boolean addrQEnable;
    private int addrS; // Apparent power
    private boolean addrSEnable;
    private int addrEa; // Total active energy
    private boolean addrEaEnable;
    private int addrEad; // Total active energy delivered
    private boolean addrEadEnable;
    private int addrEr; // Total reactive energy
    private boolean addrErEnable;
    private int addrEg; // Total generated energy
    private boolean addrEgEnable;
    private int addrEs; // Total apparent energy
    private boolean addrEsEnable;
    private boolean dataInKilo;

    private int addrI1; // Current L1
    private int addrI2; // Current L2
    private int addrI3; // Current L3

    private int addrU1; // Voltage L1
    private int addrU2; // Voltage L2
    private int addrU3; // Voltage L3

    private int addrU12; // Voltage L1-L2
    private int addrU23; // Voltage L2-L3
    private int addrU31; // Voltage L3-L1

    private boolean addrUIEnable; // Enable Voltage and Current measurement

    @JsonIgnore
    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "meterConfiguration")
    private List<MetersDb> metersDbs;

}
