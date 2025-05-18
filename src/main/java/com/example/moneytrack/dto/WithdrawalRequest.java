package com.example.moneytrack.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class WithdrawalRequest {
    private String accountNumber;
    private Long amount;
}
