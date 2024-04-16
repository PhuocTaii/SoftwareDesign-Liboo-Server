package com.btv.app.features.renewal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenewalRepository extends JpaRepository<Renewal, Long> {
}
