package com.example.moneytrack.controller;

import com.example.moneytrack.dto.MemberSignupRequest;
import com.example.moneytrack.dto.MemberSignupResponse;
import com.example.moneytrack.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    final MemberService memberService;

    // 회원가입
    @PostMapping
    public MemberSignupResponse signup(@RequestBody MemberSignupRequest requestDto) {

        MemberSignupResponse response = memberService.signup(requestDto);

        return response;

    }

    // 회원탈퇴
    @GetMapping("/{id}")
    public void MemberDeleteId(@PathVariable Integer id) {
        memberService.deleteId(id);
    }


}
