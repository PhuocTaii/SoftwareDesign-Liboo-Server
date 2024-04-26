package com.btv.app.features.transaction.models;


import com.btv.app.features.book.model.Book;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.user.models.Role;
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

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "borrowed_date", nullable = false)
    private LocalDate borrowedDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "fine")
    private Integer fine;

    @Column(name = "renewal_count")
    private Integer renewalCount;

    @PrePersist
    private void onCreate() {
        renewalCount = 0;
        fine = 0;
    }
}
