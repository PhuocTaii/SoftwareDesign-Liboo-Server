package com.btv.app.features.reservation;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.user.models.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User userId;

    @ManyToMany
    @JoinTable(
            name = "reservation_book",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();

    @Column(name = "reserved_date", nullable = false)
    private LocalDate reservedDate;

    @Column(name = "status", nullable = false, columnDefinition = "boolean default false")
    private Boolean status;

    @Column(name = "pickup_date", nullable = false)
    private LocalDate pickupDate;

    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;

    @PrePersist
    protected void onCreate() {
        this.expiredDate = LocalDate.now().plusDays(5);
    }
}
