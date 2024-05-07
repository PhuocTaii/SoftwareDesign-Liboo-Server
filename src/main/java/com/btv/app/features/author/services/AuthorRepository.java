package com.btv.app.features.author.services;

import com.btv.app.features.author.model.Author;
import com.btv.app.features.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByName(String name);

    Page<Author> findAll(Pageable pageable);

    Author findByName(String name);

}
