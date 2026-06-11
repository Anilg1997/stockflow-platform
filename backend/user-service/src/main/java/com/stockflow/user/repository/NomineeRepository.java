package com.stockflow.user.repository;

import com.stockflow.user.model.Nominee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface NomineeRepository extends JpaRepository<Nominee, UUID> {
    List<Nominee> findByUserId(UUID userId);
}
