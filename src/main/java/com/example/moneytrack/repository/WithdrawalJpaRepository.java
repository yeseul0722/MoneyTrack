package com.example.moneytrack.repository;

import com.example.moneytrack.domain.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalJpaRepository extends JpaRepository<Withdrawal, Integer> {
}
