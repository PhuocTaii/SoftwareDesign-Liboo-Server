package com.btv.app.features.reservation.model;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.user.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "reservation_book",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();

    @Column(name = "reserved_date", nullable = false)
    private LocalDate reservedDate;

    @Column(name = "status", nullable = false)
    private Integer status; //0: pending, 1: accepted, 2: rejected

    @Column(name = "pickup_date", nullable = false)
    private LocalDate pickupDate;

    @PrePersist
    protected void onCreate() {
        this.reservedDate = LocalDate.now();
        this.status = 0;
    }
}