package com.btv.app.features.book.services;

import com.btv.app.features.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    boolean existsByISBN(String isbn);


    Page<Book> findAll(Pageable pageable);

    Book findByISBN(String ISBN);

    List<Book> findByNameContainsAllIgnoreCase(String name);

    List<Book> findByISBNContainsAllIgnoreCase(String ISBN);

    List<Book> findByAuthor_Id(Long id);

    List<Book> findByPublisher_Id(Long id);



}
