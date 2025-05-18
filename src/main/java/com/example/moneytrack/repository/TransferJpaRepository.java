package com.example.moneytrack.repository;

import com.example.moneytrack.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferJpaRepository extends JpaRepository<Transfer, Integer> {
}
