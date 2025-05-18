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
    private Long balance;

    // 상품 코드
    @Column(length = 3, nullable = false)
    private String productCode;

    // 입금 테이블과 연관관계 및 입금 내역 관리
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deposit> depositStatement = new ArrayList<>();

    // 출금 테이블과 연관관계 및 출금 내역 관리
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Withdrawal> withdrawalStatement = new ArrayList<>();

    // 이체 출금 내역
    @OneToMany(mappedBy = "withdrawalAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transfer> sentTransferStatement = new ArrayList<>();

    // 이체 입금 내역
    @OneToMany(mappedBy = "depositAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transfer> receivedTransferStatement = new ArrayList<>();


    private Account(Member member, String accountNumber, Long balance, String productCode) {
        this.member = member;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.productCode = productCode;
    }

    public static Account create(Member member, String accountNumber, Long deposit, String productCode) {
        return new Account(member, accountNumber, deposit, productCode);
    }

    // 입금
    public Deposit deposit(Long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("입금액은 0보다 커야합니다.");
        }
        // 잔액 증가
        this.balance = this.balance + amount;
        // 입금 내역 생성
        Deposit deposit = Deposit.create(this, amount, this.balance);
        this.depositStatement.add(deposit);

        return deposit;
    }

    // 출금
    public void withdrawal(Long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("출금액은 0보다 커야합니다.");
        }
        if (this.balance - amount < 0) {
            throw new IllegalArgumentException("출금액은 잔액보다 클 수 없습니다. 잔액: " + balance);
        }
        this.balance = this.balance - amount;
    }

    //출금 내역
    public void withdrawalStatement(Withdrawal withdrawal) {
        withdrawalStatement.add(withdrawal);
    }

    // 이체
    public Transfer transfer(Long amount, Account targetAccount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("이체액은 0보다 커야합니다.");
        }

        // 출금 계좌 잔액보다 이체액이 많을 때
        if (this.balance - amount < 0) {
            throw new IllegalArgumentException("이체액은 잔액보다 클 수 없습니다. 잔액: " + this.balance);
        }

        // 출금 계좌에서 금액 차감
        this.balance = this.balance - amount;
        // 입금 계좌 금액 증감
        targetAccount.balance = targetAccount.balance + amount;

        // 이체 내역 생성
        Transfer transfer = Transfer.create(this, targetAccount, amount);
        this.sentTransferStatement.add(transfer);
        targetAccount.receivedTransferStatement.add(transfer);

        // 출금 내역 생성
        Withdrawal withdrawal = Withdrawal.create(this, amount, this.balance);
        withdrawalStatement.add(withdrawal);

        // 입금 내역 생성
        Deposit deposit = Deposit.create(targetAccount, amount, targetAccount.balance);
        depositStatement.add(deposit);

        return transfer;
    }
}
