package com.btv.app.features.transaction.models;


import com.btv.app.features.book.model.Book;
import com.btv.app.features.user.models.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "transaction_book",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();

    @Column(name = "borrowed_date", nullable = false)
    private LocalDate borrowedDate;

    @ElementCollection
    private List<LocalDate> dueDates = new ArrayList<>();

    @ElementCollection
    private List<LocalDate> returnDates = new ArrayList<>();

    @ElementCollection
    private List<Integer> renewalCounts = new ArrayList<>();

    @Column(name = "fine")
    private Integer fine;
    @PrePersist
    private void onCreate() {
        borrowedDate = LocalDate.now();
        fine = 0;
    }
}
