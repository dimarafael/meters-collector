package com.dima.meterscollector.repository;

import com.dima.meterscollector.domain.MeterConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterConfigRepo extends JpaRepository<MeterConfiguration, Long> {
}
