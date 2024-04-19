package com.btv.app.features.intermediate;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.transaction.models.Transaction;
import jakarta.persistence.*;

@Entity
@Table(name = "transaction_book")
public class TransactionBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

}