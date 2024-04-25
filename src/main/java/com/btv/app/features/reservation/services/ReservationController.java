package com.btv.app.features.reservation.services;

import com.btv.app.features.author.model.Author;
import com.btv.app.features.reservation.model.Reservation;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;

    @GetMapping("/librarian/getAllReservations")
    public ResponseEntity<List<Reservation>> getAllReservations(){
        try {
            List<Reservation> res = reservationService.getAllReservations();
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/reservations/getReservationsByID/{id}")
    public ResponseEntity<List<Reservation>> getAllReservations(@PathVariable("id") Long userId){
        try {
            User user = userService.getUserByID(userId);
            List<Reservation> res = reservationService.getAllReservationsByUser(user);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/reservations/getReservationByID/{id}")
    public ResponseEntity<Reservation> getReservationByID(@PathVariable("id") Long id){
        try{
            Reservation res = reservationService.getReservationByID(id);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/user/addReservation")
    public ResponseEntity<Reservation> addReservation(@ModelAttribute Reservation reservation) {
        try{
            Reservation res = reservationService.addReservation(reservation);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/reservations/modifyAuthor/{id}")
    public ResponseEntity<Reservation> modifyReservation(@PathVariable("id") Long id, @ModelAttribute Reservation reservation){
        try{
            Reservation curReser = reservationService.getReservationByID(id);
            if(curReser == null){
                return ResponseEntity.status(404).build();
            }
            Reservation res = reservationService.modifyReservation(curReser, reservation);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/reservations/deleteReservation/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable("id") Long id){
        try{
            Reservation curRes = reservationService.getReservationByID(id);
            if(curRes == null){
                return ResponseEntity.status(404).build();
            }
            reservationService.deleteReservation(id);
            return ResponseEntity.status(200).body(curRes);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
