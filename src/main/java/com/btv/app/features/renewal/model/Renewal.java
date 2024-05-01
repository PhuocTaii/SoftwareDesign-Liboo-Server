package com.btv.app.features.renewal.model;

import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.transaction.models.TransactionBook;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "renewal")
public class Renewal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private TransactionBook transactionBook;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @PrePersist
    private void onCreate() {
        this.requestDate = LocalDate.now();
    }
}
