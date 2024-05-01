package com.btv.app.features.reservation.services;

import com.btv.app.features.authentication.services.AuthenticationService;
import com.btv.app.features.author.model.Author;
import com.btv.app.features.book.model.Book;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.reservation.model.Reservation;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;
    private final AuthenticationService auth;

    @GetMapping("/librarian/all-reservations")
    public ResponseEntity<List<Reservation>> getAllReservations(){
        try {
            List<Reservation> res = reservationService.getAllReservations();
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    //GET all reservations of a user
    @GetMapping("/reservations/reservations/{id}")
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable("id") Long userId){
        try {
            User user = userService.getUserByID(userId);
            if(user == null){
                return ResponseEntity.status(404).build();
            }
            List<Reservation> res = reservationService.getAllReservationsOfUser(userId);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    //GET reservation by ID
    @GetMapping("/librarian/reservations/{id}")
    public ResponseEntity<Reservation> getReservationByID(@PathVariable("id") Long id){
        try{
            Reservation res = reservationService.getReservationByID(id);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/user/add-reservation")
    public ResponseEntity<Reservation> addReservation(@ModelAttribute Reservation reservation) {
        try{
            User user = auth.getCurrentUser();
            List<Book> books = reservation.getBooks();

            if(user == null){
                return ResponseEntity.status(404).build();
            }

            if(user.getAvailableBorrow() == 0){
                System.out.println("User is not allowed to borrow more books");
                return ResponseEntity.status(400).build();
            }

            Membership membership = user.getMembership();

            //Check booklist
            if(reservation.getBooks().size() > membership.getReserve()){
                System.out.println("User is not allowed to reserve more than " + membership.getReserve() + " books");
                return ResponseEntity.status(400).build();
            }

            //Check reservation pickup date
            if(reservation.getPickupDate().isBefore(LocalDate.now())){
                System.out.println("Pickup date must be after today");
                return ResponseEntity.status(400).build();
            }

            if(reservation.getPickupDate().isAfter(LocalDate.now().plusDays(3))){
                System.out.println("Pickup date must be within 3 days");
                return ResponseEntity.status(400).build();
            }

            //Check reservations
            List<Reservation> userReservations = reservationService.getAllReservationsOfUser(user.getId());
            List<Reservation> tmp = userReservations.stream().filter(res -> res.getPickupDate().isAfter(LocalDate.now())).toList();
            Integer cnt = 0;
            Boolean flag = false;
            for(Reservation res:tmp){
                cnt += res.getBooks().size();
                for(Book b : books){
                    if(res.getBooks().contains(b)){
                        System.out.println(b.getName() + " is already reserved");
                        flag = true;
                    }
                }
            }
            if(flag){
                return ResponseEntity.status(400).build();
            }
            if(cnt.equals(membership.getMaxBook())){
                System.out.println("User is not allowed to reserve more than " + membership.getReserve() + " books");
                return ResponseEntity.status(400).build();
            }

            //Check if book is available
            for(Book b:books){
                if(b.getQuantity() == 1){
                    System.out.println(b.getName() + " is not available");
                    books.remove(b);
                }
            }
            reservation = reservationService.modifyReservationBooks(reservation, books);



            Reservation res = reservationService.addReservation(reservation);
            user.setAvailableBorrow(user.getAvailableBorrow() - books.size());
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    //UPDATE reservation status
    @PutMapping("/librarian/update-reservation/{reservationId}")
    public ResponseEntity<Reservation> modifyReservation(@PathVariable Long reservationId, @ModelAttribute Integer status){
        try{
            Reservation curRes = reservationService.getReservationByID(reservationId);
            if(curRes == null){
                return ResponseEntity.status(404).build();
            }

            if(status == 0){
                System.out.println("Cannot set status to pending");
                return ResponseEntity.status(400).build();
            }
            Reservation res = reservationService.modifyReservationStatus(curRes, status);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

//    @PutMapping("/reservations/modifyAuthor/{id}")
//    public ResponseEntity<Reservation> modifyReservation(@PathVariable("id") Long id, @ModelAttribute Reservation reservation){
//        try{
//            Reservation curReser = reservationService.getReservationByID(id);
//            if(curReser == null){
//                return ResponseEntity.status(404).build();
//            }
//            Reservation res = reservationService.modifyReservation(curReser, reservation);
//            return ResponseEntity.status(200).body(res);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).build();
//        }
//    }
//
//    @DeleteMapping("/reservations/deleteReservation/{id}")
//    public ResponseEntity<Reservation> deleteReservation(@PathVariable("id") Long id){
//        try{
//            Reservation curRes = reservationService.getReservationByID(id);
//            if(curRes == null){
//                return ResponseEntity.status(404).build();
//            }
//            reservationService.deleteReservation(id);
//            return ResponseEntity.status(200).body(curRes);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).build();
//        }
//    }
}
