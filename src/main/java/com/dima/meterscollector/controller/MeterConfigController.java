package com.dima.meterscollector.controller;

import com.dima.meterscollector.domain.MeterConfiguration;
import com.dima.meterscollector.exceptions.NotFoundException;
import com.dima.meterscollector.model.PollMeters;
import com.dima.meterscollector.repository.MeterConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/meter_config")
public class MeterConfigController {

    @Autowired
    private MeterConfigRepo meterConfigRepo;

    @Autowired
    private PollMeters pollMeters;

    @GetMapping
    public Iterable<MeterConfiguration> getAllMeters(){
        return meterConfigRepo.findAll();
    }

    @GetMapping(value = "/{id}")
    public MeterConfiguration getMeter(@PathVariable long id){
        Optional<MeterConfiguration> meterConf =  meterConfigRepo.findById(id);
        if(meterConf.isPresent()){
            return meterConf.get();
        } else {
            throw new NotFoundException();
        }
    }

    @PostMapping
    public MeterConfiguration addMeter(@RequestBody MeterConfiguration meterConf){
        meterConf.setId(null); // protect from editing existing
        meterConfigRepo.save(meterConf);
        pollMeters.setMeterConfigActual(false);
        return meterConf;
    }

    @PutMapping(value = "/{id}")
    public MeterConfiguration putMeter(@PathVariable long id, @RequestBody MeterConfiguration meterConf){
        MeterConfiguration updateMeterConf = meterConfigRepo.findById(id)
                .orElseThrow(NotFoundException::new);
        updateMeterConf.setPosition(meterConf.getPosition());
        updateMeterConf.setTitleEn(meterConf.getTitleEn());
        updateMeterConf.setTitleHu(meterConf.getTitleHu());
        updateMeterConf.setPollingEnable(meterConf.isPollingEnable());
        updateMeterConf.setIpAddress(meterConf.getIpAddress());
        updateMeterConf.setUnitId(meterConf.getUnitId());
        updateMeterConf.setAddrP(meterConf.getAddrP());
        updateMeterConf.setAddrPEnable(meterConf.isAddrPEnable());
        updateMeterConf.setAddrQ(meterConf.getAddrQ());
        updateMeterConf.setAddrQEnable(meterConf.isAddrQEnable());
        updateMeterConf.setAddrS(meterConf.getAddrS());
        updateMeterConf.setAddrSEnable(meterConf.isAddrSEnable());
        updateMeterConf.setAddrEa(meterConf.getAddrEa());
        updateMeterConf.setAddrEaEnable(meterConf.isAddrEaEnable());
        updateMeterConf.setAddrEad(meterConf.getAddrEad());
        updateMeterConf.setAddrEadEnable(meterConf.isAddrEadEnable());
        updateMeterConf.setAddrEr(meterConf.getAddrEr());
        updateMeterConf.setAddrErEnable(meterConf.isAddrErEnable());
        updateMeterConf.setAddrEg(meterConf.getAddrEg());
        updateMeterConf.setAddrEgEnable(meterConf.isAddrEgEnable());
        updateMeterConf.setAddrEs(meterConf.getAddrEs());
        updateMeterConf.setAddrEsEnable(meterConf.isAddrEsEnable());
        updateMeterConf.setDataInKilo(meterConf.isDataInKilo());

        updateMeterConf.setAddrI1(meterConf.getAddrI1());
        updateMeterConf.setAddrI2(meterConf.getAddrI2());
        updateMeterConf.setAddrI3(meterConf.getAddrI3());
        updateMeterConf.setAddrU1(meterConf.getAddrU1());
        updateMeterConf.setAddrU2(meterConf.getAddrU2());
        updateMeterConf.setAddrU3(meterConf.getAddrU3());
        updateMeterConf.setAddrU12(meterConf.getAddrU12());
        updateMeterConf.setAddrU23(meterConf.getAddrU23());
        updateMeterConf.setAddrU31(meterConf.getAddrU31());
        updateMeterConf.setAddrUIEnable(meterConf.isAddrUIEnable());

        meterConfigRepo.save(updateMeterConf);
        pollMeters.setMeterConfigActual(false);
        return meterConf;
    }

    @DeleteMapping("/{id}")
    public void deleteMeter(@PathVariable long id){
        if(!meterConfigRepo.existsById(id)){
            throw new NotFoundException();
        }
        meterConfigRepo.deleteById(id);
        pollMeters.setMeterConfigActual(false);
    }
}
