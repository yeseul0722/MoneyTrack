package com.example.moneytrack.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSearchByAccountNumberRequest {
    private String accountNumber;
}
