package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Account;
import com.example.moneytrack.domain.Transfer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TransferResponse {
    private final String withdrawalAccountNumber;
    private final Long balance;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime transferAt;

    private TransferResponse(String withdrawalAccountNumber, Long balance, LocalDateTime transferAt) {
        this.withdrawalAccountNumber = withdrawalAccountNumber;
        this.balance = balance;
        this.transferAt = transferAt;
    }

    public static TransferResponse from(Transfer transfer, Account withdrawalAccount) {
        return new TransferResponse(
                withdrawalAccount.getAccountNumber(),
                withdrawalAccount.getBalance(),
                transfer.getTransferAt()
        );
    }
}
