package com.dima.meterscollector.repository;

import com.dima.meterscollector.domain.MetersDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetersDbRepo extends JpaRepository<MetersDb, Long> {
}
