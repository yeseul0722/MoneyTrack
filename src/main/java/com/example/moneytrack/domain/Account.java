package com.example.moneytrack.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    // 계좌 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer id;

    // 회원 테이블과 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 계좌번호
    @Column(unique = true, length = 14, nullable = false)
    private String accountNumber;

    // 잔액
    @Column(nullable = false)
    private Long balance;

    // 상품 코드
    @Column(length = 3, nullable = false)
    private String productCode;

    private Account(Member member, String accountNumber, Long balance, String productCode) {
        this.member = member;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.productCode = productCode;
    }

    public static Account create(Member member, String accountNumber, Long deposit, String productCode) {
        return new Account(member, accountNumber, deposit, productCode);
    }
    
}
