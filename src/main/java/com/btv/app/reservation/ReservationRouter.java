package com.btv.app.reservation;

import com.btv.app.transaction.Transaction;
import com.btv.app.transaction.TransactionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/reservation")
public class ReservationRouter {
    private final ReservationController reservationController;
    @Autowired
    public ReservationRouter(ReservationController reservationController) {
        this.reservationController = reservationController;
    }

    @GetMapping("/getAllReservations")
    public List<Reservation> getAllReservations(){
        return reservationController.getAllReservations();
    }

    @GetMapping("/getReservationByID/{id}")
    public Reservation getReservationByID(@PathVariable("id") Long id){
        return reservationController.getReservationByID(id);
    }
}
