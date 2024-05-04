package com.btv.app.features.reservation.services;

import com.btv.app.features.author.model.Author;
import com.btv.app.features.book.model.Book;
import com.btv.app.features.reservation.model.Reservation;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation addReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }
    public Reservation modifyReservationStatus(Reservation res, Integer status){
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

    public List<Reservation> getAllReservationsOfUser(Long userId){
        return reservationRepository.findByUserId_Id(userId);
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
