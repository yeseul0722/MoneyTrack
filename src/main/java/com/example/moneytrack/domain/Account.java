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
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    // 상품 코드
    @Column(length = 3, nullable = false)
    private String productCode;

    // 입금 테이블과 연관관계 및 입금 내역 관리
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deposit> depositStatement = new ArrayList<>();

    // 출금 테이블과 연관관계 및 출금 내역 관리
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Withdrawal> withdrawalStatement = new ArrayList<>();

    private Account(Member member, String accountNumber, BigDecimal balance, String productCode) {
        this.member = member;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.productCode = productCode;
    }

    public static Account create(Member member, String accountNumber, BigDecimal deposit, String productCode) {
        return new Account(member, accountNumber, deposit, productCode);
    }

    // 입금
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("입금액은 0보다 커야합니다.");
        }
        this.balance = this.balance.add(amount);
    }

    // 출금
    public void withdrawal(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("출금액은 0보다 커야합니다.");
        }
        if (this.balance.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("출금액은 잔액보다 클 수 없습니다. 잔액: " + balance);
        }
        this.balance = this.balance.subtract(amount);
    }


    // 입금 내역
    public void depositStatement(Deposit deposit) {
        depositStatement.add(deposit);
    }

    //출금 내역
    public void withdrawalStatement(Withdrawal withdrawal) {
        withdrawalStatement.add(withdrawal);
    }

}
