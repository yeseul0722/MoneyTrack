package com.example.moneytrack.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TransferRequest {
    private String withdrawalAccountNumber;
    private String depositAccountNumber;
    private Long amount;
}
