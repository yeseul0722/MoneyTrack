package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Deposit;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DepositResponse {
    private final String accountNumber;
    private final Long balance;
    private final LocalDate depositedAt;

    private DepositResponse(String accountNumber, Long balance, LocalDate depositedAt) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.depositedAt = depositedAt;
    }

    public static DepositResponse from(Deposit deposit) {
        return new DepositResponse(
                deposit.getDepositAccountNumber(),
                deposit.getBalance(),
                deposit.getDepositedAt()
        );
    }
}
