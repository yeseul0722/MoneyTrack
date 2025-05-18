package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Deposit;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DepositResponse {
    private final String accountNumber;
    private final Long balance;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime depositedAt;

    private DepositResponse(String accountNumber, Long balance, LocalDateTime depositedAt) {
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
