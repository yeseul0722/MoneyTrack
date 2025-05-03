package com.example.moneytrack.controller;

import com.example.moneytrack.dto.AccountCreateRequest;
import com.example.moneytrack.dto.AccountCreateResponse;
import com.example.moneytrack.dto.MemberInfoResponse;
import com.example.moneytrack.dto.MemberSearchByAccountNumberRequest;
import com.example.moneytrack.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    //계좌 생성
    @PostMapping
    public AccountCreateResponse createAccount(@RequestBody AccountCreateRequest request) {
        AccountCreateResponse response = accountService.createAccount(request);

        return response;
    }

    //계좌로 회원 검색
    @PostMapping("/search")
    public MemberInfoResponse findByAccountNumber(@RequestBody MemberSearchByAccountNumberRequest request) {
        MemberInfoResponse response = accountService.findByAccountNumber(request.getAccountNumber());

        return response;
    }

    @DeleteMapping("/{accountNumber}")
    public void deleteAccount(@PathVariable String accountNumber) {
        accountService.deleteAccount(accountNumber);
    }


}
