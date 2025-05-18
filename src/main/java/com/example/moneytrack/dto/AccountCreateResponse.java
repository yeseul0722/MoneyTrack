package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Account;
import lombok.Getter;

@Getter
public class AccountCreateResponse {
    private final String accountNumber;
    private final Long balance;

    private AccountCreateResponse(String accountNumber, Long balance) {
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
