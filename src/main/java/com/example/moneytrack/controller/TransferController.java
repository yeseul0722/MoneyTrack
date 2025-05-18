package com.example.moneytrack.controller;

import com.example.moneytrack.dto.TransferRequest;
import com.example.moneytrack.dto.TransferResponse;
import com.example.moneytrack.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    final TransferService transferService;

    @PostMapping
    public TransferResponse transfer(@RequestBody TransferRequest request) {

        TransferResponse response = transferService.transfer(request);

        return response;
    }
}
