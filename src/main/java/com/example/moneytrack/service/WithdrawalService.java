package com.example.moneytrack.service;

import com.example.moneytrack.domain.Account;
import com.example.moneytrack.domain.Withdrawal;
import com.example.moneytrack.dto.WithdrawalRequest;
import com.example.moneytrack.dto.WithdrawalResponse;
import com.example.moneytrack.repository.AccountJpaRepository;
import com.example.moneytrack.repository.WithdrawalJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WithdrawalService {

    private final WithdrawalJpaRepository withdrawalRepository;
    private final AccountJpaRepository accountRepository;

    @Transactional
    public WithdrawalResponse withdrawal(WithdrawalRequest request) {

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber()).orElseThrow(() -> new IllegalArgumentException("해당 계좌가 존재하지 않습니다. 계좌번호: " + request.getAccountNumber()));
        account.withdrawal(request.getAmount());
        Withdrawal withdrawal = Withdrawal.create(account, request.getAmount(), account.getBalance());
        account.withdrawalStatement(withdrawal);
        withdrawalRepository.save(withdrawal);

        return WithdrawalResponse.from(withdrawal);

    }
}
