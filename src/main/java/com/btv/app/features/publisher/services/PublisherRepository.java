package com.btv.app.features.publisher.services;

import com.btv.app.features.author.model.Author;
import com.btv.app.features.publisher.model.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    boolean existsByName(String name);

    Publisher findByName(String name);

    Page<Publisher> findAll(Pageable pageable);



}
