package com.btv.app.intermediate;

import com.btv.app.book.Book;
import com.btv.app.reservation.Reservation;
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
