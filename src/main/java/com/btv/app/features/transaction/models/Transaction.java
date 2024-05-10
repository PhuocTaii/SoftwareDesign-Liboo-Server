package com.btv.app.features.transaction.models;


import com.btv.app.features.user.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "transaction")
    private List<TransactionBook> transactionBooks;

    @Column(name = "borrowed_date", nullable = false)
    private LocalDate borrowedDate;

    @Column(name = "fine")
    private Integer fine;
    @PrePersist
    private void onCreate() {
        borrowedDate = LocalDate.now();
        fine = 0;
    }
}
