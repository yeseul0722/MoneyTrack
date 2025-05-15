package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Account;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AccountCreateResponse {
    private final String accountNumber;
    private final BigDecimal balance;

    private AccountCreateResponse(String accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public static AccountCreateResponse from(Account account) {
        return new AccountCreateResponse(
                account.getAccountNumber(),
                account.getBalance()
        );
    }
}
