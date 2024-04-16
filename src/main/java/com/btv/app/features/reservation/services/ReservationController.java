package com.btv.app.features.reservation.services;

import com.btv.app.features.reservation.model.Reservation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/getAllReservations")
    public List<Reservation> getAllReservations(){
        try {
            return reservationService.getAllReservations();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/getReservationByID/{id}")
    public Reservation getReservationByID(@PathVariable("id") Long id){
        try{
            return reservationService.getReservationByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
