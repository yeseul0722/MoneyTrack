package com.example.moneytrack.repository;

import com.example.moneytrack.domain.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findTopByProductCodeOrderByAccountNumberDesc(String productCode);
    Optional<Account> findByAccountNumber(String accountNumber);
}
