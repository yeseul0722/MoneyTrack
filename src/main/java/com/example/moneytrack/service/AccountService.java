package com.example.moneytrack.service;

import com.example.moneytrack.domain.Account;
import com.example.moneytrack.domain.Member;
import com.example.moneytrack.dto.AccountCreateRequest;
import com.example.moneytrack.dto.AccountCreateResponse;
import com.example.moneytrack.dto.MemberInfoResponse;
import com.example.moneytrack.repository.AccountJpaRepository;
import com.example.moneytrack.repository.MemberJpaRepository;
import com.example.moneytrack.util.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountJpaRepository accountRepository;
    private final MemberJpaRepository memberRepository;

    private static final String bankCode = "001";

    @Transactional
    public AccountCreateResponse createAccount(AccountCreateRequest request) {

        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        String productCode = request.getProductCode();
        String recentAccountNumber = accountRepository.findTopByProductCodeOrderByAccountNumberDesc(productCode).map(Account::getAccountNumber).orElse(null);
        BigDecimal deposit = request.getDeposit() != null ? request.getDeposit() : BigDecimal.ZERO;

        String newAccountNumber = AccountNumberGenerator.generate(bankCode, productCode, recentAccountNumber);

        Account newAccount = Account.create(member, newAccountNumber,deposit, productCode);
        accountRepository.save(newAccount);

        return AccountCreateResponse.from(newAccount);
    }

    @Transactional
    public MemberInfoResponse findByAccountNumber(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new IllegalArgumentException("해당 계좌가 존재하지 않습니다. 계좌번호: " + accountNumber));

        Member member = account.getMember();

        return MemberInfoResponse.from(member);
    }

    @Transactional
    public void deleteAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new IllegalArgumentException("해당 계좌가 존재하지 않습니다. 계좌번호: " + accountNumber));

        accountRepository.delete(account);
    }
}
