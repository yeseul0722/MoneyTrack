package com.example.moneytrack.service;

import com.example.moneytrack.domain.Account;
import com.example.moneytrack.domain.Transfer;
import com.example.moneytrack.dto.TransferRequest;
import com.example.moneytrack.dto.TransferResponse;
import com.example.moneytrack.repository.AccountJpaRepository;
import com.example.moneytrack.repository.TransferJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferJpaRepository transferRepository;
    private final AccountJpaRepository accountRepository;

    // 계좌 이체
    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        // 출금 계좌 조회
        Account withdrawalAccount = accountRepository.findByAccountNumber(request.getWithdrawalAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("해당 계좌가 존재하지 않습니다. 계좌번호: " + request.getWithdrawalAccountNumber()));
        // 입금 계좌 조회
        Account depositAccount = accountRepository.findByAccountNumber(request.getDepositAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("해당 계좌가 존재하지 않습니다. 계좌번호: " + request.getDepositAccountNumber()));

        // 계좌 이체
        Transfer transfer = withdrawalAccount.transfer(request.getAmount(), depositAccount);
        transferRepository.save(transfer);

        return TransferResponse.from(transfer, withdrawalAccount);

    }
}
