package com.btv.app.features.renewal.services;

import com.btv.app.features.renewal.model.Renewal;
import com.btv.app.features.user.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RenewalRepository extends JpaRepository<Renewal, Long> {
    Page<Renewal> findByTransactionBook_Transaction_User(User user, Pageable pageable);

    Page<Renewal> findByTransactionBook_Transaction_UserAndRequestDateBetween(User user, LocalDate requestDateStart, LocalDate requestDateEnd, Pageable pageable);
}
