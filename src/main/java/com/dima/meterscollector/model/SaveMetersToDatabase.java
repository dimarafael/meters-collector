package com.dima.meterscollector.model;

import com.dima.meterscollector.domain.MetersDb;
import com.dima.meterscollector.repository.MetersDbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SaveMetersToDatabase {

    @Autowired
    public void setMetersDbRepo(MetersDbRepo metersDbRepo) {
        this.metersDbRepo = metersDbRepo;
    }
    private MetersDbRepo metersDbRepo;

    @Autowired
    public void setPollMeters(PollMeters pollMeters){
        this.pollMeters = pollMeters;
    }
    private PollMeters pollMeters;

    @Scheduled(cron = "0 */5 * * * *")
    private void saveMeters(){
        List<MeterData> meterDataList = new ArrayList<>(pollMeters.getMeterDataList());
        for(MeterData meterData : meterDataList){
            MetersDb metersDb = new MetersDb();
            metersDb.setEa(meterData.getEa());
            metersDb.setEad(meterData.getEad());
            metersDb.setEr(meterData.getEr());
            metersDb.setEg(meterData.getEg());
            metersDb.setEs(meterData.getEs());
            metersDb.setMeterConfiguration(
                    pollMeters.getMeterConfigurations().stream()
                            .filter(configuration -> meterData.getId() == configuration.getId())
                            .findAny().orElse(null));
            if(metersDb.getMeterConfiguration() != null){
                metersDbRepo.save(metersDb);
            }
        }
    }
}
