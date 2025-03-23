package com.example.moneytrack.repository;

import com.example.moneytrack.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmail(String email);
}
