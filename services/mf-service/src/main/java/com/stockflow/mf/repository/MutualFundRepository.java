package com.stockflow.mf.repository;

import com.stockflow.mf.model.MutualFund;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface MutualFundRepository extends JpaRepository<MutualFund, UUID> {
    List<MutualFund> findByCategory(String category);
    List<MutualFund> findByFundHouse(String fundHouse);
    List<MutualFund> findBySchemeNameContainingIgnoreCase(String name);
}
