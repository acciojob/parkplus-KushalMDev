package com.driver.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.entities.Payment;
import com.driver.entities.Reservation;
import com.driver.entities.Spot;
import com.driver.model.PaymentMode;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Payment payment=new Payment();
        Reservation reservation=reservationRepository2.findById(reservationId).get();
        Spot spot=reservation.getSpot();
        int numberOfHours=reservation.getNumberOfHours();
        int pricePerHour=spot.getPricePerHour();
        if(amountSent<numberOfHours*pricePerHour)return null;
        payment.setPaymentCompleted(true);
        if(mode.equals("cash")){
            payment.setPaymentMode(PaymentMode.CASH);
        }
        else if(mode.equals("card")){
            payment.setPaymentMode(PaymentMode.CARD);
        }
        else{
            payment.setPaymentMode(PaymentMode.UPI);
        }
        spot.setOccupied(false);
        reservation.setPayment(payment);
        reservationRepository2.save(reservation);
        return payment;

    }
}
