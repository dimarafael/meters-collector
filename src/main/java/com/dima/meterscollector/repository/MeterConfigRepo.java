package com.dima.meterscollector.repository;

import com.dima.meterscollector.domain.MeterConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterConfigRepo extends JpaRepository<MeterConfiguration, Long> {
}
