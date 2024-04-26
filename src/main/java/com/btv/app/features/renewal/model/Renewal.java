package com.btv.app.features.renewal.model;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.user.models.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "renewal")
public class Renewal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

//    @ManyToOne
//    @JoinColumn(name = "book_id", nullable = false)
//        private Book bookId;
//
    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @PrePersist
    private void onCreate() {
        this.requestDate = LocalDate.now();
    }
}
