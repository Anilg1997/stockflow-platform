package com.stockflow.ipo.repository;

import com.stockflow.ipo.model.IpoApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface IpoApplicationRepository extends JpaRepository<IpoApplication, UUID> {
    List<IpoApplication> findByUserId(UUID userId);
    List<IpoApplication> findByIpoId(UUID ipoId);
}
