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
public class Withdrawal {

    // 출금아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "withdrawal_id")
    private Integer withdrawalId;

    // 출금 계좌
    @Column(length = 14, nullable = false)
    private String withdrawalAccountNumber;

    // 출금 금액
    @Column(nullable = false, precision = 19, scale = 2)
    private Long amount;

    // 잔액
    @Column(nullable = false, precision = 19, scale = 2)
    private Long balance;

    // 출금 시각
    @Column(nullable = false)
    private LocalDate withdrawalAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public static Withdrawal create(Account account, Long amount, Long balance) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.account = account;
        withdrawal.withdrawalAccountNumber = account.getAccountNumber();
        withdrawal.amount = amount;
        withdrawal.balance = balance;
        withdrawal.withdrawalAt = LocalDate.now();

        return withdrawal;
    }
}
