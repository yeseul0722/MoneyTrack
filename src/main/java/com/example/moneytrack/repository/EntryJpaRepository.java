package com.example.moneytrack.repository;

import com.example.moneytrack.domain.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryJpaRepository extends JpaRepository<Entry, Long> {
    // 특정 계좌의 라인(입출/이체 포함) 최신순
    Page<Entry> findByAccount_IdOrderByOccurredAtDescIdDesc(Integer accountId, Pageable pageable);
    List<Entry> findByAccount_Member_idOrderByOccurredAtDesc(Integer memberId);

}
