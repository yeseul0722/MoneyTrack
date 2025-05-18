package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Withdrawal;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WithdrawalResponse {
    private final String accountNumber;
    private final Long balance;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime withdrawalAt;

    private WithdrawalResponse(String accountNumber, Long balance, LocalDateTime withdrawalAt) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.withdrawalAt = withdrawalAt;
    }

    public static WithdrawalResponse from(Withdrawal withdrawal) {
        return new WithdrawalResponse(
                withdrawal.getWithdrawalAccountNumber(),
                withdrawal.getBalance(),
                withdrawal.getWithdrawalAt()
        );
    }
}
