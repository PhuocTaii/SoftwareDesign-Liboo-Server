package com.btv.app.features.transaction.services;

import com.btv.app.features.transaction.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByUserId_Id(Long id, Pageable pageable);

    Page<Transaction> findByUser_IdAndBorrowedDateBetween(Long id, LocalDate borrowedDateStart, LocalDate borrowedDateEnd, Pageable pageable);
    Page<Transaction> findByUser_IdAndTransactionBooks_ReturnDateBetween(Long id, LocalDate returnDateStart, LocalDate returnDateEnd, Pageable pageable);
    Page<Transaction> findByUser_IdAndTransactionBooks_DueDateBetween(Long id, LocalDate dueDateStart, LocalDate dueDateEnd, Pageable pageable);



    long countDistinctByTransactionBooks_ReturnDateNull();

    Page<Transaction> findByBorrowedDateBetween(LocalDate borrowedDateStart, LocalDate borrowedDateEnd, Pageable pageable);
    Page<Transaction> findByTransactionBooks_ReturnDateBetween(LocalDate returnDateStart, LocalDate returnDateEnd, Pageable pageable);

    Page<Transaction> findByTransactionBooks_DueDateBetween(LocalDate dueDateStart, LocalDate dueDateEnd, Pageable pageable);
}
