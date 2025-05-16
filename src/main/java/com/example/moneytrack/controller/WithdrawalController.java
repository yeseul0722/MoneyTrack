package com.example.moneytrack.controller;

import com.example.moneytrack.dto.WithdrawalRequest;
import com.example.moneytrack.dto.WithdrawalResponse;
import com.example.moneytrack.service.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/withdrawal")
public class WithdrawalController {

    final WithdrawalService withdrawalService;

    @PostMapping
    public WithdrawalResponse withdrawal(@RequestBody WithdrawalRequest request) {

        WithdrawalResponse response = withdrawalService.withdrawal(request);

        return response;
    }
}
