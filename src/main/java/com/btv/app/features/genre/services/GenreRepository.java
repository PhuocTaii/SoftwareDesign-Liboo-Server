package com.btv.app.features.genre.services;

import com.btv.app.features.author.model.Author;
import com.btv.app.features.genre.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    boolean existsByName(String name);

    Genre findByName(String name);

    Page<Genre> findAll(Pageable pageable);

}
