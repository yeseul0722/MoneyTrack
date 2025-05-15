package com.example.moneytrack.service;

import com.example.moneytrack.domain.Account;
import com.example.moneytrack.domain.Deposit;
import com.example.moneytrack.dto.DepositRequest;
import com.example.moneytrack.dto.DepositResponse;
import com.example.moneytrack.repository.AccountJpaRepository;
import com.example.moneytrack.repository.DepositJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final DepositJpaRepository depositRepository;
    private final AccountJpaRepository accountRepository;

    @Transactional
    public DepositResponse deposit(DepositRequest request) {

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber()).orElseThrow(() -> new IllegalArgumentException("해당 계좌가 존재하지 않습니다."));
        account.deposit(request.getAmount());
        Deposit deposit = Deposit.create(account, request.getAmount(), account.getBalance());
        account.depositStatement(deposit);
        depositRepository.save(deposit);

        return DepositResponse.from(deposit);
    }
}
