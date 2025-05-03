package com.example.moneytrack.controller;

import com.example.moneytrack.dto.AccountCreateRequest;
import com.example.moneytrack.dto.AccountCreateResponse;
import com.example.moneytrack.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

        private final AccountService accountService;

        @PostMapping
        public AccountCreateResponse createAccount(@RequestBody AccountCreateRequest requestDto) {
                AccountCreateResponse response = accountService.createAccount(requestDto);

                return response;
        }

}
