package com.example.moneytrack.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class DepositRequest {
    private String accountNumber;
    private BigDecimal amount;
}
