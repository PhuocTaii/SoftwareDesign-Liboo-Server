package com.btv.app.features.transaction.services;

import com.btv.app.features.intermediate.TransactionBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionBookRepository extends JpaRepository<TransactionBook, Long> {
}
