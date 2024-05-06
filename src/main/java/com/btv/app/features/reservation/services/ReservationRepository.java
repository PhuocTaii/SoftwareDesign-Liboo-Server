package com.btv.app.features.reservation.services;

import com.btv.app.features.reservation.model.Reservation;
import com.btv.app.features.user.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByUser_Id(Long id, Pageable pageable);
    List<Reservation> findByUser_IdAndPickupDateAfter(Long id, LocalDate pickupDate);
    Page<Reservation> findByUser_IdAndReservedDateBetween(Long id, LocalDate reservedDateStart, LocalDate reservedDateEnd, Pageable pageable);
    Page<Reservation> findByUser_IdAndPickupDateBetween(Long id, LocalDate pickupDateStart, LocalDate pickupDateEnd, Pageable pageable);
}
