package com.btv.app.features.reservation.services;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.reservation.model.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final Integer PAGE_SIZE = 10;

    public Reservation addReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }
    public Reservation modifyReservationStatus(Reservation res, Boolean status){
        res.setStatus(status);
        return reservationRepository.save(res);
    }

    public Reservation modifyReservationBooks(Reservation res, List<Book> books){
        res.setBooks(books);
        return reservationRepository.save(res);
    }

    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public Page<Reservation> getReservationsOfUser(Long userId, int pageNumber){
        return reservationRepository.findByUser_Id(userId, PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public List<Reservation> getActiveReservationsByUser(Long userId, LocalDate date){
        return reservationRepository.findByUser_IdAndPickupDateAfter(userId, date);
    }

    public Reservation getReservationByID(Long id){
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        return optionalReservation.orElse(null);
    }

    public void deleteReservation(Long id){
        reservationRepository.deleteById(id);
    }

    public List<Reservation> pendingReservations(){
        List<Reservation> res = new ArrayList<>();
        List<Reservation> allRes = reservationRepository.findAll();
        for (Reservation r : allRes){
            if(r.getStatus() == 0){
                res.add(r);
            }
        }
        return res;
    }
}
