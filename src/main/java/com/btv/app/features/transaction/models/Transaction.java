package com.btv.app.features.transaction.models;


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

    @OneToMany(mappedBy = "transaction")
    private List<TransactionBook> transactionBooks = new ArrayList<>();

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
