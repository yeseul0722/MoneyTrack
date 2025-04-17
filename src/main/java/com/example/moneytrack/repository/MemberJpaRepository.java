package com.example.moneytrack.repository;

import com.example.moneytrack.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Integer> {
    //이메일 조회
    Optional<Member> findByEmail(String email);
    // 이름 + 생년월일 조회
    Optional<Member> findByNameAndDateOfBirth(String name, LocalDate dateOfBirth);


}
