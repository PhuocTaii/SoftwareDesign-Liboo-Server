package com.btv.app.features.user.services;

import com.btv.app.features.user.models.Role;
import com.btv.app.features.user.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail (String email);

    Boolean existsByEmail(String email);
    Boolean existsByIdentifier(String identifier);

    User findByIdentifier(String identifier);

    Page<User> findByRole(Role role, Pageable pageable);

    Page<User> findByRoleAndEmailContainsAllIgnoreCase(Role role, String email, Pageable pageable);

    Page<User> findByRoleAndNameContainsAllIgnoreCase(Role role, String name, Pageable pageable);

    long countByRole(Role role);

}