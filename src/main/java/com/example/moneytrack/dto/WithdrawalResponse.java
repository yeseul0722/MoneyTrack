package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Withdrawal;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WithdrawalResponse {
    private final String accountNumber;
    private final Long balance;
    private final LocalDate withdrawalAt;

    private WithdrawalResponse(String accountNumber, Long balance, LocalDate withdrawalAt) {
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
