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


@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountJpaRepository accountRepository;
    private final MemberJpaRepository memberRepository;

    private static final String bankCode = "001";

    @Transactional
    public AccountCreateResponse createAccount(AccountCreateRequest request) {
        // 멤버 아이디로 멤버 객체 가져오기
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        // 상품 조회
        String productCode = request.getProductCode();
        // 최신 계좌 조회
        String recentAccountNumber = accountRepository.findTopByProductCodeOrderByAccountNumberDesc(productCode)
                .map(Account::getAccountNumber).orElse(null);
        // 입금액 유무에 따라 값 할당
        Long deposit = request.getDeposit() != null ? request.getDeposit() : 0;
        // 계좌번호 생성
        String newAccountNumber = AccountNumberGenerator.generate(bankCode, productCode, recentAccountNumber);
        // 계좌 객체 생성 및 저장
        Account newAccount = Account.create(member, newAccountNumber,deposit, productCode);
        accountRepository.save(newAccount);

        return AccountCreateResponse.from(newAccount);
    }

    // 계좌번호로 회원 조회
    @Transactional
    public MemberInfoResponse findByAccountNumber(String accountNumber) {
        // 계좌번호로 계좌 객체 조회
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 계좌가 존재하지 않습니다. 계좌번호: " + accountNumber));
        // 해당 계좌의 멤버 객체 조회
        Member member = account.getMember();

        return MemberInfoResponse.from(member);
    }

    // 계좌 삭제
    @Transactional
    public void deleteAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 계좌가 존재하지 않습니다. 계좌번호: " + accountNumber));

        accountRepository.delete(account);
    }
}
