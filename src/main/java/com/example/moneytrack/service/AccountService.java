package com.example.moneytrack.service;

import com.example.moneytrack.domain.Account;
import com.example.moneytrack.domain.Member;
import com.example.moneytrack.dto.AccountCreateRequest;
import com.example.moneytrack.dto.AccountCreateResponse;
import com.example.moneytrack.repository.AccountJpaRepository;
import com.example.moneytrack.repository.MemberJpaRepository;
import com.example.moneytrack.util.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountJpaRepository accountRepository;
    private final MemberJpaRepository memberRepository;

    private static final String bankCode = "001";

    @Transactional
    public AccountCreateResponse createAccount(AccountCreateRequest request) {

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        String productCode = request.getProductCode();

        String recentAccountNumber = accountRepository
                .findTopByProductCodeOrderByAccountNumberDesc(productCode)
                .map(Account::getAccountNumber)
                .orElse(null);


        String newAccountNumber = AccountNumberGenerator.generate(bankCode, productCode, recentAccountNumber);

        Account newAccount = Account.create(member, newAccountNumber, productCode);
        accountRepository.save(newAccount);

        return AccountCreateResponse.from(newAccount);
    }
}
