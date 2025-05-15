package com.example.moneytrack.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deposit {

    // 입금아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposit_id")
    private Integer depositId;

    // 입금 계좌
    @Column(length = 14, nullable = false)
    private String depositAccountNumber;

    // 입금 금액
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    // 잔액
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    // 입금 시각
    @Column(nullable = false)
    private LocalDate depositedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public static Deposit create(Account account, BigDecimal amount, BigDecimal balance) {
        Deposit deposit = new Deposit();
        deposit.account = account;
        deposit.depositAccountNumber = account.getAccountNumber();
        deposit.amount = amount;
        deposit.balance = balance;
        deposit.depositedAt = LocalDate.now();

        return deposit;
    }


}
