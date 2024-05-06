package com.btv.app.features.reservation.services;

import com.btv.app.exception.MyException;
import com.btv.app.features.authentication.services.AuthenticationService;
import com.btv.app.features.book.model.Book;
import com.btv.app.features.book.services.BookService;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.reservation.model.Reservation;
import com.btv.app.features.reservation.model.ReservationRequest;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;
    private final BookService bookService;
    private final AuthenticationService auth;

    @AllArgsConstructor
    public static class ReservationListResponse {
        public List<Reservation> reservations;
        public int pageNumber;
        public int totalPages;
        public long totalItems;
    }

    @AllArgsConstructor
    private static class ReservationResponse {
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
    @GetMapping("/librarian/reservations/user/{id}")
    public ResponseEntity<ReservationListResponse> getUserReservations(@PathVariable("id") Long userId, @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber){
        User user = userService.getUserByID(userId);
        if(user == null){
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        }
        Page<Reservation> res = reservationService.getReservationsOfUser(userId, pageNumber);
        return ResponseEntity.ok(new ReservationController.ReservationListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }

    @GetMapping("/user/reservations")
    public ResponseEntity<ReservationListResponse> getReservationsByCurrentUser(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "filter-by", required = false) String filterOption,
            @RequestParam(value = "from", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateFrom,
            @RequestParam(value = "to", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateTo
    ){
        System.out.println("filterOption" + filterOption);
        System.out.println("dateFrom" + dateFrom);
        System.out.println("dateTo" + dateTo);

        User user = auth.getCurrentUser();
        Page<Reservation> res;

        if(Objects.equals(filterOption, "") || dateFrom == null || dateTo == null)
            res = reservationService.getReservationsOfUser(user.getId(), pageNumber);
        else if(Objects.equals(filterOption, "reserve-date"))
            res = reservationService.getReservationsOfUserByReserveDate(user.getId(), dateFrom, dateTo, pageNumber);
        else if(Objects.equals(filterOption, "pickup-date"))
            res = reservationService.getReservationsOfUserByPickupDate(user.getId(), dateFrom, dateTo, pageNumber);
        else
            res = reservationService.getReservationsOfUser(user.getId(), pageNumber);

        return ResponseEntity.ok(new ReservationController.ReservationListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }

    //GET reservation by ID
    @GetMapping("/librarian/reservations/{id}")
    public ResponseEntity<Reservation> getReservationByID(@PathVariable("id") Long id){
        Reservation res = reservationService.getReservationByID(id);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/user/add-reservation")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        List<String> announcements = new ArrayList<>();
        Reservation reservation = new Reservation();
        User user = auth.getCurrentUser();
        reservation.setUser(user);
        reservation.setPickupDate(request.getPickupDate());

        List<Book> books = new ArrayList<>();
        for(String i : request.getIsbn()){
            Book book = bookService.getBookByISBN(i);
            if(book == null){
                throw new MyException(HttpStatus.NOT_FOUND, "Book not found");
            }
            books.add(book);
        }
        reservation.setBooks(books);
        String announcement = "";

        if(user == null){
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        }

        if(user.getAvailableBorrow() == 0){
            throw new MyException(HttpStatus.BAD_REQUEST, "You are not allowed to borrow more books");
        }

        Membership membership = user.getMembership();

        //Check booklist
        if(reservation.getBooks().size() > membership.getReserve()){
            throw new MyException(HttpStatus.BAD_REQUEST, "You are not allowed to reserve more than " + membership.getReserve() + " books");
        }

        //Check reservation pickup date
        if(reservation.getPickupDate().isBefore(LocalDate.now())){
            throw new MyException(HttpStatus.BAD_REQUEST, "Pickup date must be after today");
        }

        if(reservation.getPickupDate().isAfter(LocalDate.now().plusDays(3))){
            throw new MyException(HttpStatus.BAD_REQUEST, "Pickup date must be within 3 days");
        }

        //Check reservations
        List<Reservation> userReservations = reservationService.getActiveReservationsByUser(user.getId(), LocalDate.now());
//        List<Reservation> tmp = userReservations.stream().filter(res -> res.getPickupDate().isAfter(LocalDate.now())).toList();
        Integer cnt = 0;
        Boolean flag = false;
        for(Reservation res:userReservations){
            cnt += res.getBooks().size();
            for(Book b : books){
                if(res.getBooks().contains(b)){
                    announcements.add(b.getName() + " ");
                    flag = true;
                }
            }
        }
        if(flag){
            String message = "You have already reserved these books : " + announcements;
            throw new MyException(HttpStatus.BAD_REQUEST, message);
        }
        if(cnt.equals(membership.getMaxBook())){
            String message = "You are not allowed to reserve more than " + membership.getReserve() + " books";
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

        ReservationResponse res = new ReservationResponse(reserve, announcement);

        userService.decreaseAvailableBorrow(user, books.size());
        return ResponseEntity.ok(res);
    }

    //UPDATE reservation status
    @PutMapping("/librarian/update-reservation/{reservationId}")
    public ResponseEntity<Reservation> modifyReservation(@PathVariable Long reservationId, @ModelAttribute Boolean status){
        Reservation curRes = reservationService.getReservationByID(reservationId);
        if(curRes == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Reservation not found");
        }

        if(!status){
            throw new MyException(HttpStatus.BAD_REQUEST, "Cannot set status to NOT-PICKED-UP");
        }

        Reservation res = reservationService.modifyReservationStatus(curRes, true);
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/librarian/pending-reservations")
    public ResponseEntity <List<Reservation>> getPendingReservations(){
        List<Reservation> res = reservationService.pendingReservations();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/user/not-picked-up-reservations")
    public ResponseEntity <List<Reservation>> getNotPickedUpReservations(){
        User user = auth.getCurrentUser();
        List<Reservation> res = reservationService.getNotPickedUpReservationsByUser(user.getId(), LocalDate.now());
        return ResponseEntity.ok(res);
    }
}
