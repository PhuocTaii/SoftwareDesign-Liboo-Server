package com.btv.app.features.reservation.services;

import com.btv.app.features.reservation.model.Reservation;
import com.btv.app.features.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
//    List<Reservation> findByUserId(Long userId);
    List<Reservation> findAllByUserId(User userId);
}
