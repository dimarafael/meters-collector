package com.dima.meterscollector.controller;

import com.dima.meterscollector.domain.MeterConfiguration;
import com.dima.meterscollector.exceptions.NotFoundException;
import com.dima.meterscollector.repository.MeterConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/meter_config")
public class MeterConfigController {

    @Autowired
    private MeterConfigRepo meterConfigRepo;

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
        return meterConf;
    }

    @PutMapping(value = "/{id}")
    public MeterConfiguration putMeter(@PathVariable long id, @RequestBody MeterConfiguration meterConf){
        MeterConfiguration updateMeterConf = meterConfigRepo.findById(id)
                .orElseThrow(NotFoundException::new);
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
        updateMeterConf.setAddrEr(meterConf.getAddrEr());
        updateMeterConf.setAddrErEnable(meterConf.isAddrErEnable());
        updateMeterConf.setAddrEg(meterConf.getAddrEg());
        updateMeterConf.setAddrEgEnable(meterConf.isAddrEgEnable());
        updateMeterConf.setAddrEs(meterConf.getAddrEs());
        updateMeterConf.setAddrEsEnable(meterConf.isAddrEsEnable());
        meterConfigRepo.save(updateMeterConf);
        return meterConf;
    }

    @DeleteMapping("/{id}")
    public void deleteMeter(@PathVariable long id){
        meterConfigRepo.deleteById(id);
    }
}
