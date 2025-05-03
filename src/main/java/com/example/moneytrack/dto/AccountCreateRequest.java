package com.example.moneytrack.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountCreateRequest {
    private String productCode;
    private Integer memberId;
}
