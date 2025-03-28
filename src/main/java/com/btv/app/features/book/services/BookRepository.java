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

    Page<Book> findByStatus(Boolean status, Pageable pageable);


    Book findByISBN(String ISBN);

    List<Book> findByNameContainsAllIgnoreCase(String name);

    List<Book> findByISBNContainsAllIgnoreCase(String ISBN);

    List<Book> findByAuthor_Id(Long id);

    List<Book> findByPublisher_Id(Long id);


    Page<Book> findByISBNContainsAllIgnoreCase(String ISBN, Pageable pageable);
    Page<Book> findByNameContainsAllIgnoreCase(String name, Pageable pageable);

    Page<Book> findByAuthor_NameContainsAllIgnoreCase(String name, Pageable pageable);

    Page<Book> findByPublisher_NameContainsAllIgnoreCase(String name, Pageable pageable);

    Page<Book> findByGenres_NameContainsAllIgnoreCase(String name, Pageable pageable);

    Page<Book> findByISBNContainsAndStatusAllIgnoreCase(String ISBN, Boolean status, Pageable pageable);

    Page<Book> findByNameContainsAndStatusAllIgnoreCase(String name, Boolean status, Pageable pageable);


}
