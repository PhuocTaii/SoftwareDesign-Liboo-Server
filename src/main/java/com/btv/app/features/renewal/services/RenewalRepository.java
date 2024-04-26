package com.btv.app.features.renewal.services;

import com.btv.app.features.renewal.model.Renewal;
import com.btv.app.features.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RenewalRepository extends JpaRepository<Renewal, Long> {
}
