package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Account;
import com.example.moneytrack.domain.Transaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DepositResponse {
    private final Long transactionId;
    private final String accountNumber;
    private final Long balance;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime occurredAt;

    public static DepositResponse from(Transaction transaction, Account to) {
        return new DepositResponse(
                transaction.getId(),
                to.getAccountNumber(),
                to.getBalance(),
                transaction.getOccurredAt()
        );
    }
}
