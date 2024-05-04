package com.btv.app.features.reservation.services;

import com.btv.app.exception.MyException;
import com.btv.app.features.authentication.services.AuthenticationService;
import com.btv.app.features.author.model.Author;
import com.btv.app.features.book.model.Book;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.reservation.model.Reservation;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;
    private final AuthenticationService auth;

    @AllArgsConstructor
    private static class reservationResponse {
        public Reservation reservation;
        public String message;
    }

    @GetMapping("/librarian/all-reservations")
    public ResponseEntity<List<Reservation>> getAllReservations(){
        List<Reservation> res = reservationService.getAllReservations();
        if(res == null)
            throw new MyException(HttpStatus.NOT_FOUND, "No reservation found");
        return ResponseEntity.ok(res);
    }

    //GET all reservations of a user
    @GetMapping("/reservations/reservations/{id}")
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable("id") Long userId){
        User user = userService.getUserByID(userId);
        if(user == null){
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        }
        List<Reservation> res = reservationService.getAllReservationsOfUser(userId);
        return ResponseEntity.ok(res);
    }

    //GET reservation by ID
    @GetMapping("/librarian/reservations/{id}")
    public ResponseEntity<Reservation> getReservationByID(@PathVariable("id") Long id){
        Reservation res = reservationService.getReservationByID(id);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/user/add-reservation")
    public ResponseEntity<reservationResponse> addReservation(@ModelAttribute Reservation reservation) {
        List<String> announcements = new ArrayList<>();

        User user = auth.getCurrentUser();
        List<Book> books = reservation.getBooks();

        String announcement = "";

        if(user == null){
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        }

        if(user.getAvailableBorrow() == 0){
            throw new MyException(HttpStatus.BAD_REQUEST, "User is not allowed to borrow more books");
        }

        Membership membership = user.getMembership();

        //Check booklist
        if(reservation.getBooks().size() > membership.getReserve()){
            throw new MyException(HttpStatus.BAD_REQUEST, "User is not allowed to reserve more than " + membership.getReserve() + " books");
        }

        //Check reservation pickup date
        if(reservation.getPickupDate().isBefore(LocalDate.now())){
            throw new MyException(HttpStatus.BAD_REQUEST, "Pickup date must be after today");
        }

        if(reservation.getPickupDate().isAfter(LocalDate.now().plusDays(3))){
            throw new MyException(HttpStatus.BAD_REQUEST, "Pickup date must be within 3 days");
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
                    announcements.add(b.getName() + " ");
                    flag = true;
                }
            }
        }
        if(flag){
            String message = "User has already reserved these books : " + announcements;
            throw new MyException(HttpStatus.BAD_REQUEST, message);
        }
        if(cnt.equals(membership.getMaxBook())){
            String message = "User is not allowed to reserve more than " + membership.getReserve() + " books";
            throw new MyException(HttpStatus.BAD_REQUEST, message);
        }

        //Check if book is available
        for(Book b:books){
            if(b.getQuantity() == 1){
                announcements.add(b.getName() + " ");
                books.remove(b);
            }
        }
        reservation = reservationService.modifyReservationBooks(reservation, books);

        if(announcements.size() > 0){
            announcement = "These books are not available : " + announcements;
        } else{
            announcement = "Success!";
        }

        Reservation reserve = reservationService.addReservation(reservation);

        reservationResponse res = new reservationResponse(reserve, announcement);

        user.setAvailableBorrow(user.getAvailableBorrow() - books.size());

        return ResponseEntity.ok(res);
    }

    //UPDATE reservation status
    @PutMapping("/librarian/update-reservation/{reservationId}")
    public ResponseEntity<Reservation> modifyReservation(@PathVariable Long reservationId, @ModelAttribute Integer status){
        Reservation curRes = reservationService.getReservationByID(reservationId);
        if(curRes == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Reservation not found");
        }

        if(status == 0){
            throw new MyException(HttpStatus.BAD_REQUEST, "Cannot set status to pending");
        }

        Reservation res = reservationService.modifyReservationStatus(curRes, status);
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/librarian/pending-reservations")
    public ResponseEntity <List<Reservation>> getPendingReservations(){
        List<Reservation> res = reservationService.pendingReservations();
        return ResponseEntity.ok(res);
    }
}
