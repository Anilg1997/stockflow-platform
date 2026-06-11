package com.stockflow.ipo.repository;

import com.stockflow.ipo.model.Ipo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface IpoRepository extends JpaRepository<Ipo, UUID> {
    List<Ipo> findByStatusOrderByOpenDateDesc(String status);
    List<Ipo> findByOrderByOpenDateDesc();
}
