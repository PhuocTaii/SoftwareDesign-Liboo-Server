package com.btv.app.features.transaction.services;

import com.btv.app.features.transaction.models.TransactionBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface TransactionBookRepository extends JpaRepository<TransactionBook, Long> {
    TransactionBook findByTransaction_IdAndBook_Id(Long id, Long id1);

    List<TransactionBook> findByTransaction_User_IdAndBook_Id(Long userId, Long bookId);

    boolean existsByTransaction_User_IdAndBook_IdAndReturnDateNull(Long userId, Long bookId);

    List<TransactionBook> findByReturnDateNull();

    TransactionBook findFirstByTransaction_User_IdAndBook_ISBNOrderByIdDesc(Long id, String ISBN);

}
