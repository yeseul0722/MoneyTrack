package com.example.moneytrack.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepositRequest {

    @NotNull
    private String accountNumber;

    @NotNull @Positive
    private Long amount;

    private String description;
    private String CorrelationId;
}
