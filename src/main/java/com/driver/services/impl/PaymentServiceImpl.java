package com.driver.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.model.Spot;
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
        String s=mode.toLowerCase();
        if(!s.equals("cash") && !s.equals("card") && !s.equals("upi")){
            throw new Exception("Payment mode not detected");
        }
        if(amountSent<numberOfHours*pricePerHour)throw new Exception("Insufficient Amount");
        payment.setPaymentCompleted(true);
        if(s.equals("cash")){
            payment.setPaymentMode(PaymentMode.CASH);
        }
        else if(s.equals("card")){
            payment.setPaymentMode(PaymentMode.CARD);
        }
        else{
            payment.setPaymentMode(PaymentMode.UPI);
        }
        payment.setReservation(reservation);
        spot.setOccupied(false);
        reservation.setPayment(payment);
        reservationRepository2.save(reservation);
        return payment;

    }
}
