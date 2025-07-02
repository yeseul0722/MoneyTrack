package com.example.moneytrack.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountCreateRequest {
    private String productCode;
    private Integer memberId;
    private Long deposit;

    public AccountCreateRequest(String productCode, Integer memberId, Long deposit) {
        this.productCode = productCode;
        this.memberId = memberId;
        this.deposit = deposit;
    }
}
