package com.example.moneytrack.controller;

import com.example.moneytrack.dto.*;
import com.example.moneytrack.service.LedgerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ledger")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;

    // 멱등키가 없으면 서버에서 생성
    private String ensureCid(String cid) {
        return StringUtils.hasText(cid) ? cid : UUID.randomUUID().toString();
    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponse> deposit(@Valid @RequestBody DepositRequest request) {
        DepositResponse response = ledgerService.deposit(
                request.getAccountNumber(),
                request.getAmount(),
                request.getDescription(),
                ensureCid(request.getCorrelationId())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/withdraw")
    public  ResponseEntity<WithdrawResponse> withdraw(@Valid @RequestBody WithdrawRequest request) {
        WithdrawResponse response = ledgerService.withdraw(
                request.getAccountNumber(),
                request.getAmount(),
                request.getDescription(),
                ensureCid(request.getCorrelationId())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request) {
        TransferResponse response = ledgerService.transfer(
//                request.getRequestMemberId(),
                request.getWithdrawAccountNumber(),
                request.getDepositAccountNumber(),
                request.getAmount(),
                request.getDescription(),
                ensureCid(request.getCorrelationId())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //회원 모든 거래내역 조회
    @GetMapping("/members/{memberId}/entries")
    public MemberEntryResponse getMemberEntries(@PathVariable Integer memberId) {
        return ledgerService.getMemberEntries(memberId);
    }
}
