package com.example.moneytrack.service;

import com.example.moneytrack.domain.Account;
import com.example.moneytrack.domain.Deposit;
import com.example.moneytrack.dto.DepositRequest;
import com.example.moneytrack.dto.DepositResponse;
import com.example.moneytrack.repository.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final AccountJpaRepository accountRepository;

    // 입금
    @Transactional
    public DepositResponse deposit(DepositRequest request) {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("해당 계좌가 존재하지 않습니다."));

        Deposit deposit = account.deposit(request.getAmount());

        return DepositResponse.from(deposit);
    }
}
