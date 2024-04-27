package com.btv.app.features.renewal.model;

import com.btv.app.features.transaction.models.Transaction;
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

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @PrePersist
    private void onCreate() {
        this.requestDate = LocalDate.now();
    }
}
