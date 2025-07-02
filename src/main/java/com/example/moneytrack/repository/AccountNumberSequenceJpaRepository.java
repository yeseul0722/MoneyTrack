package com.example.moneytrack.repository;

import com.example.moneytrack.domain.AccountNumberSequence;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountNumberSequenceJpaRepository extends JpaRepository<AccountNumberSequence, Long> {
    // 배타적 잠금을 획득하고 데이터가 다른 트랜잭션에서 read, update, delete 되는 것 방지
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM AccountNumberSequence s WHERE s.id =:id")
    AccountNumberSequence findByIdForUpdate(@Param("id") Integer id);
}
