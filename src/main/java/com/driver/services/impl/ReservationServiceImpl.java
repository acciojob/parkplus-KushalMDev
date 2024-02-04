package com.driver.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.ParkingLot;
import com.driver.model.Payment;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.model.User;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {

        Optional<User> puser=userRepository3.findById(userId);
        Optional<ParkingLot> pOptional=parkingLotRepository3.findById(parkingLotId);
        if(puser.isEmpty() || pOptional.isEmpty())throw new Exception("Cannot make reservation");
        User user=puser.get();
        ParkingLot parkingLot=pOptional.get();
        List<Spot> spots=parkingLot.getSpotList();
        SpotType needSpotType;
        if(numberOfWheels<=2){
            needSpotType=SpotType.TWO_WHEELER;
        }
        else if(numberOfWheels<=4){
            needSpotType=SpotType.FOUR_WHEELER;
        }
        else{
            needSpotType=SpotType.OTHERS;
        }
        int minPrice=Integer.MAX_VALUE;
        Spot bookSpot=null;
        for(Spot spot:spots){
            SpotType spotType=spot.getSpotType();
            
            if(spot.getOccupied()==false){
               if(needSpotType.equals(SpotType.TWO_WHEELER)){
                if(timeInHours*spot.getPricePerHour()<minPrice){
                    minPrice=timeInHours*spot.getPricePerHour();
                    bookSpot=spot;
                }
               }
                if(needSpotType.equals(SpotType.FOUR_WHEELER) && (spot.getSpotType().equals(SpotType.FOUR_WHEELER) || spot.getSpotType().equals(SpotType.OTHERS))){
                if(timeInHours*spot.getPricePerHour()<minPrice){
                    minPrice=timeInHours*spot.getPricePerHour();
                    bookSpot=spot;
                }
               }
               else{
                if(spot.getSpotType().equals(SpotType.OTHERS)){
                    if(timeInHours*spot.getPricePerHour()<minPrice){
                        minPrice=timeInHours*spot.getPricePerHour();
                        bookSpot=spot;
                    }
                }
               }
            }
        }
        if(bookSpot.equals(null))throw new Exception("Cannot make reservation");
        Reservation reservation=new Reservation();
        reservation.setNumberOfHours(timeInHours);
        reservation.setSpot(bookSpot);
        reservation.setUser(user);
        user.getReservationList().add(reservation);
        bookSpot.setOccupied(true);
        bookSpot.getReservationList().add(reservation);
        reservationRepository3.save(reservation);
        userRepository3.save(user);
        spotRepository3.save(bookSpot);
        return reservation;

    }
}
