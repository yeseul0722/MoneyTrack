package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Account;
import com.example.moneytrack.domain.Transaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WithdrawResponse {
    private final Long transactionId;
    private final String accountNumber;
    private final Long balance;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime occurredAt;

    public static WithdrawResponse from(Transaction transaction, Account from) {
        return new WithdrawResponse(
                transaction.getId(),
                from.getAccountNumber(),
                from.getBalance(),
                transaction.getOccurredAt()
        );
    }
}
