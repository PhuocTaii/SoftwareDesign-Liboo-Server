package com.btv.app.features.user.services;

import com.btv.app.features.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail (String email);

    Boolean existsByEmail(String email);
    Boolean existsByIdentifier(String identifier);

    User findByIdentifier(String identifier);

}