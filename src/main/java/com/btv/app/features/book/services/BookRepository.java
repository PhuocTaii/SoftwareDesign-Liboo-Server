package com.btv.app.features.book.services;

import com.btv.app.features.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    boolean existsByISBN(String isbn);
}
