package com.example.moneytrack.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountInfoRequest {
    private Integer accountId;
    private Integer memberId;
    private String accountNumber;
    private Long balance;
    private String productCode;

    public AccountInfoRequest(Integer accountId, Integer memberId, String accountNumber, Long balance, String productCode) {
        this.accountId = accountId;
        this.memberId = memberId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.productCode = productCode;
    }
}
