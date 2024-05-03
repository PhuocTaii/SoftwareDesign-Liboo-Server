package com.btv.app.features.transaction.services;

import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.user.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByUserId_Id(Long id, Pageable pageable);
}
