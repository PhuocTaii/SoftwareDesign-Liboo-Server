package com.btv.app.features.intermediate;

import com.btv.app.features.book.Book;
import com.btv.app.features.reservation.Reservation;
import jakarta.persistence.*;

@Entity
@Table(name = "reservation_book")
public class ReservationBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
