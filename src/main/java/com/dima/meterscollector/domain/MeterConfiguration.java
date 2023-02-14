package com.dima.meterscollector.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
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
    private int addrEr; // Total reactive energy
    private boolean addrErEnable;
    private int addrEg; // Total generated energy
    private boolean addrEgEnable;
    private int addrEs; // Total apparent energy
    private boolean addrEsEnable;
    private boolean dataInKilo;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "meterConfiguration")
    private List<MetersDb> metersDbs;

}
