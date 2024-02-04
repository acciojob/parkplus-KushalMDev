package com.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.driver.entities.ParkingLot;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Integer>{
}
