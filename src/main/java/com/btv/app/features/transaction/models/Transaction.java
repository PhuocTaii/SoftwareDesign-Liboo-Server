package com.btv.app.features.transaction.models;


import com.btv.app.features.book.model.Book;
import com.btv.app.features.intermediate.TransactionBook;
import com.btv.app.features.user.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "transaction_book",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();

    @Column(name = "borrowed_date")
    private LocalDate borrowedDate;

//    @ElementCollection
//    private List<LocalDate> dueDates = new ArrayList<>();
//
//    @ElementCollection
//    private List<LocalDate> returnDates = new ArrayList<>();
//
//    @ElementCollection
//    private List<Integer> renewalCounts = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(
//            name = "transaction_book",
//            joinColumns = @JoinColumn(name = "transaction_id"),
//            inverseJoinColumns = @JoinColumn(name = "transaction_books_id")
//    )
//    @ManyToMany
//    @JoinTable(
//            name = "transaction_book",
//            joinColumns = @JoinColumn(name = "transaction_id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id")
//    )
    @OneToMany(mappedBy = "transaction")
    private List<TransactionBook> transactionBooks = new ArrayList<>();

    @Column(name = "fine")
    private Integer fine;
    @PrePersist
    private void onCreate() {
        borrowedDate = LocalDate.now();
        fine = 0;
    }
}
