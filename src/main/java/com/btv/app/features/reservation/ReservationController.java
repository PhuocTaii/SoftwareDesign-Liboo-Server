package com.btv.app.features.reservation;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public List<Reservation> getAllReservations(){
        try {
            return reservationService.getAllReservations();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Reservation getReservationByID(Long id){
        try{
            return reservationService.getReservationByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
