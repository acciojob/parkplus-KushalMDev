package com.driver.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot=new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
       return parkingLotRepository1.save(parkingLot);

    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot spot=new Spot();
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
        spot.setPricePerHour(pricePerHour);
        spot.setParkingLot(parkingLot);
        spot.setOccupied(false);
        if(numberOfWheels<=2){
            spot.setSpotType(SpotType.TWO_WHEELER);
        }
        else if(numberOfWheels<=4){
            spot.setSpotType(SpotType.FOUR_WHEELER);
        }
        else{
            spot.setSpotType(SpotType.OTHERS);
        }
        parkingLot.getSpotList().add(spot);
        parkingLotRepository1.save(parkingLot);

        return spot;


    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Spot spot=null;
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
        for(Spot spot1: parkingLot.getSpotList()) {
            if(spot1.getId()==spotId) {
                spot=spot1;
                spot.setPricePerHour(pricePerHour);
                spotRepository1.save(spot);
                break;
            }
        }
        return spot;


    }

    @Override
    public void deleteParkingLot(int parkingLotId) {

        parkingLotRepository1.deleteById(parkingLotId);
    }
}
