package com.example.moneytrack.controller;

import com.example.moneytrack.dto.MemberInfoResponse;
import com.example.moneytrack.dto.MemberSignupRequest;
import com.example.moneytrack.dto.MemberSignupResponse;
import com.example.moneytrack.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    // 회원 검색
    @GetMapping("/search/by-info")
    public MemberInfoResponse findByNameAndDateOfBirth(@RequestParam String name, LocalDate dateOfBirth) {
        MemberInfoResponse response = memberService.findByNameAndDateOfBirth(name, dateOfBirth);

        return response;
    }

    @GetMapping("/search/by-email")
    public MemberInfoResponse findByEmail(@RequestParam String email) {
        MemberInfoResponse response = memberService.findByEmail(email);

        return response;
    }

}
