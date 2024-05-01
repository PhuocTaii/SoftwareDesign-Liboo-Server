package com.btv.app.features.transaction.services;

import com.btv.app.features.transaction.models.TransactionBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionBookRepository extends JpaRepository<TransactionBook, Long> {

    TransactionBook findByTransaction_IdAndBook_Id(Long id, Long id1);
}
