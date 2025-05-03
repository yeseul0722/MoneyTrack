package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AccountCreateResponse {
    private final String accountNumber;

    private AccountCreateResponse(String accountNumber) {this.accountNumber = accountNumber;}

    public static AccountCreateResponse from(Account account) {
        return new AccountCreateResponse(
                account.getAccountNumber()
        );
    }
}
