package com.example.moneytrack.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class WithdrawalRequest {
    private String accountNumber;
    private BigDecimal amount;
}
