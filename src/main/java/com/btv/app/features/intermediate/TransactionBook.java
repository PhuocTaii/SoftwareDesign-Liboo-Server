package com.btv.app.features.intermediate;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.transaction.models.Transaction;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transaction_book")
public class TransactionBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    @JsonBackReference
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "renewal_count")
    private Integer renewalCount;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "due_date")
    private LocalDate dueDate;


//    @PrePersist
//    private void onCreate() {
//        this.renewalCount = 0;
//        this.returnDate = LocalDate.EPOCH;
//        this.dueDate = LocalDate.now().plusDays(30);
//    }
}