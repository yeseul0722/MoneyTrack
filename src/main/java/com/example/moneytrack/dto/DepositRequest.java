package com.example.moneytrack.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepositRequest {
    private String accountNumber;
    private Long amount;
}
