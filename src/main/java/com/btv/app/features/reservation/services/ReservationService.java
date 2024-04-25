package com.btv.app.features.reservation.services;

import com.btv.app.features.author.model.Author;
import com.btv.app.features.reservation.model.Reservation;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation addReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }
    public Reservation modifyReservation(Reservation curRes, Reservation updateRes){
        if (updateRes.getStatus() != null) {
            curRes.setStatus(updateRes.getStatus());
        }
        if (updateRes.getBooks() != null) {
            curRes.setBooks(updateRes.getBooks());
        }
        if (updateRes.getReservedDate() != null) {
            curRes.setReservedDate(updateRes.getReservedDate());
        }
        if (updateRes.getPickupDate() != null) {
            curRes.setPickupDate(updateRes.getPickupDate());
        }
        if (updateRes.getExpiredDate() != null) {
            curRes.setExpiredDate(updateRes.getExpiredDate());
        }

        return reservationRepository.save(curRes);
    }
    public List<Reservation> getAllReservations(){
        try {
            return reservationRepository.findAll();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public List<Reservation> getAllReservationsByUser(User user){
        try {
//            return reservationRepository.findByUserId(user.getId());
            System.out.println(user);
            return reservationRepository.findAllByUserId(user);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Reservation getReservationByID(Long id){
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        return optionalReservation.orElse(null);
    }

    public void deleteReservation(Long id){
        reservationRepository.deleteById(id);
    }
}
