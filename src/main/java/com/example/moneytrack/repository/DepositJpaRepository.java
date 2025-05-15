package com.example.moneytrack.repository;

import com.example.moneytrack.domain.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositJpaRepository extends JpaRepository<Deposit, Integer> {
}
