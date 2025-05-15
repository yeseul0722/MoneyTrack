package com.example.moneytrack.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class AccountCreateRequest {
    private String productCode;
    private Integer memberId;
    private BigDecimal deposit;
}
