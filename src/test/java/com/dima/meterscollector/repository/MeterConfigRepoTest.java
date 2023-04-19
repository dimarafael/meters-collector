package com.dima.meterscollector.repository;

import com.dima.meterscollector.domain.MeterConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;


@DataJpaTest
class MeterConfigRepoTest {

    @Autowired
    private MeterConfigRepo meterConfigRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void testGetConfig(){
        //given
        MeterConfiguration savedConfiguration = testEntityManager.persistFlushFind(
                MeterConfiguration.builder().ipAddress("10.10.10.10").build());

        //when
        Optional<MeterConfiguration> meterConfiguration = meterConfigRepo.findById(savedConfiguration.getId());

        //then
        then(meterConfiguration.isPresent()).isTrue();
        then(savedConfiguration.getIpAddress()).isEqualTo(meterConfiguration.get().getIpAddress());
    }
}
