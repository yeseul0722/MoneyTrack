package com.example.moneytrack.controller;

import com.example.moneytrack.dto.MemberSignupRequest;
import com.example.moneytrack.dto.MemberSignupResponse;
import com.example.moneytrack.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    final MemberService memberService;

    @PostMapping
    public MemberSignupResponse signup(@RequestBody MemberSignupRequest requestDto) {

        MemberSignupResponse response = memberService.signup(requestDto);

        return response;

    }


}
