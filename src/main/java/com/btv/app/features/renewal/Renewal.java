package com.btv.app.features.renewal;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.user.models.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "renewal")
public class Renewal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User userId;

    @OneToOne
    private Book bookId;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "status", nullable = false, columnDefinition = "boolean default false")
    private Boolean status;

    @Column(name = "approve_date", nullable = false)
    private LocalDate approveDate;

    @PrePersist
    protected void onCreate() {
        this.requestDate = LocalDate.now();
    }
}
