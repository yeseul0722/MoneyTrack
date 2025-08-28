package com.example.moneytrack.service;

import com.example.moneytrack.domain.Account;
import com.example.moneytrack.domain.Entry;
import com.example.moneytrack.domain.Transaction;
import com.example.moneytrack.domain.enums.Direction;
import com.example.moneytrack.domain.enums.TransactionStatus;
import com.example.moneytrack.domain.enums.TransactionType;
import com.example.moneytrack.dto.*;
import com.example.moneytrack.repository.AccountJpaRepository;
import com.example.moneytrack.repository.EntryJpaRepository;
import com.example.moneytrack.repository.TransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final AccountJpaRepository accountRepository;
    private final TransactionJpaRepository transactionRepository;
    private final EntryJpaRepository entryRepository;

    // 공통 유틸

    private void validateAmount(Long amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("거래 금액이 올바르지 않습니다.");
        }
        if (amount > 10_000_000_000L) {
            throw new ConcurrencyFailureException("비정상적으로 큰 금액입니다.");
        }
    }

    private void applyBalance(Account account, Direction direction, Long amount) {
        long balance = account.getBalance();
        balance += (direction == Direction.CREDIT ? amount : -amount);

        if (balance < 0) throw new IllegalArgumentException("잔액이 부족합니다. accountId=" + account.getId());
        account.setBalance(balance);
    }

    // 입금
    @Transactional
    public DepositResponse deposit(String accountNumber, Long amount, String description, String correlationId) {
        validateAmount(amount);

        // 단일 계좌만 배타락으로 확보
        Account to = accountRepository.findByAccountNumber(accountNumber).orElseThrow();

        // 헤더 생성
        Transaction transaction = Transaction.newHeader(description, TransactionType.DEPOSIT, correlationId);

        // 라인 1개
        transaction.addEntry(Entry.of(to, Direction.CREDIT, amount, transaction.getOccurredAt(), "deposit"));

        // 잔액 반영
        applyBalance(to, Direction.CREDIT, amount);

        // 완료
        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);
        return DepositResponse.from(transaction, to);

    }

    // 출금
    @Transactional
    public WithdrawResponse withdraw(String accountNumber, Long amount, String description, String correlationId) {
        validateAmount(amount);

        // 단일 계좌만 배타락으로 확보
        Account from = accountRepository.findByAccountNumber(accountNumber).orElseThrow();

        // 잔액 검증
        if (from.getBalance() < amount) {
            throw new IllegalArgumentException("잔액 부족: fromAccountId=" + from.getAccountNumber());
        }

        // 헤더 생성
        Transaction transaction = Transaction.newHeader(description, TransactionType.WITHDRAW, correlationId);

        // 라인 1개
        transaction.addEntry(Entry.of(from, Direction.DEBIT, amount, transaction.getOccurredAt(), "withdraw"));

        // 잔액 반영
        applyBalance(from, Direction.DEBIT, amount);

        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);

        return WithdrawResponse.from(transaction, from);
    }

    // 이체
    @Transactional
    public TransferResponse transfer(String withdrawAccountNumber, String depositAccountNumber, Long amount, String description, String correlationId) {
        if (withdrawAccountNumber.equals(depositAccountNumber)) {
            throw new IllegalArgumentException("동일 계좌 간 이체는 불가합니다.");
        }
        validateAmount(amount);


        // 교착 방지: 계좌번호 사전순으로 먼저/나중 순서 결정
        String firstNum = (withdrawAccountNumber.compareTo(depositAccountNumber) < 0)
                ? withdrawAccountNumber : depositAccountNumber;
        String secondNum = (firstNum.equals(withdrawAccountNumber))
                ? depositAccountNumber : withdrawAccountNumber;

        // 락 순서대로 조회
        Account first = accountRepository.findByAccountNumber(firstNum).orElseThrow();
        Account second = accountRepository.findByAccountNumber(secondNum).orElseThrow();

        Account from = withdrawAccountNumber.equals(first.getAccountNumber()) ? first : second;
        Account to = depositAccountNumber.equals(first.getAccountNumber()) ? first : second;

//        // 소유자 검증
//        if (!from.getMember().getId().equals(requestMemberId)) {
//            throw new SecurityException("본인 계좌에서만 출금할 수 있습니다.");
//        }

        long sumBefore = from.getBalance() + to.getBalance();

        // 헤더 생성
        Transaction transaction = Transaction.newHeader(description, TransactionType.TRANSFER, correlationId);

        // 라인 2줄
        transaction.addEntry(Entry.of(from, Direction.DEBIT, amount, transaction.getOccurredAt(), "transfer: from"));
        transaction.addEntry(Entry.of(to, Direction.CREDIT, amount, transaction.getOccurredAt(), "transfer: to"));

        // 잔액 반영
        applyBalance(from, Direction.DEBIT, amount);
        applyBalance(to, Direction.CREDIT, amount);

        // 전후 합 체크
        long sumAfter = from.getBalance() + to.getBalance();
        if (sumBefore != sumAfter) {
            throw new IllegalStateException("잔액 합 불일치(이체)");
        }

        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);
        return TransferResponse.from(transaction, from, to, amount);
    }

    // 조회
    @Transactional(readOnly = true)
    public Page<Entry> getEntries(Integer accountId, Pageable pageable) {
        return entryRepository.findByAccount_IdOrderByOccurredAtDescIdDesc(accountId, pageable);
    }

    // 고객 거래내역 조회
    @Transactional(readOnly = true)
    public MemberEntryResponse getMemberEntries(Integer memberId) {
        List<Entry> entries = entryRepository.findByAccount_Member_idOrderByOccurredAtDesc(memberId);
        List<EntryDetail> details = entries.stream()
                .map(EntryDetail::from)
                .toList();

        // 모든 계좌를 조회하여 전액 합산
        Long total = accountRepository.findByMember_Id(memberId).stream()
                .mapToLong(Account::getBalance)
                .sum();

        return MemberEntryResponse.builder()
                .totalBalance(total)
                .entries(details)
                .build();
    }
}
