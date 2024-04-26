package com.btv.app.features.transaction.services;

import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId_Id(Long id);
    Transaction findByBookId_Id(Long id);

    Transaction findByUserId_IdAndBookId_Id(Long userId, Long bookId);

}
