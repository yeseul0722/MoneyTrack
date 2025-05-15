package com.example.moneytrack.controller;

import com.example.moneytrack.dto.DepositRequest;
import com.example.moneytrack.dto.DepositResponse;
import com.example.moneytrack.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deposit")
public class DepositController {

    final DepositService depositService;

    @PostMapping
    public DepositResponse deposit(@RequestBody DepositRequest request) {

        DepositResponse response = depositService.deposit(request);

        return response;
    }
}
