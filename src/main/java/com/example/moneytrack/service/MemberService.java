package com.example.moneytrack.service;

import com.example.moneytrack.domain.Member;
import com.example.moneytrack.dto.MemberInfoResponse;
import com.example.moneytrack.dto.MemberSignupRequest;
import com.example.moneytrack.dto.MemberSignupResponse;
import com.example.moneytrack.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository memberRepository;

    // 회원가입
    @Transactional
    public MemberSignupResponse signup(MemberSignupRequest request) {

        Member member = new Member.Builder(request.getEmail(), request.getPassword(), request.getName(), request.getDateOfBirth()).gender(request.getGender()).build();

        // 중복 회원 검증
        vaildateDuplicateMember(member);

        Integer id = memberRepository.save(member).getId();

        MemberSignupResponse response = new MemberSignupResponse();
        response.setId(id);

        return response;
    }

    // 중복 회원 검증
    private void vaildateDuplicateMember(Member member) {
        memberRepository.findByEmail(member.getEmail()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    // 이름 + 생년월일로 검색
    @Transactional(readOnly = true)
    public MemberInfoResponse findByNameAndDateOfBirth(String name, LocalDate dateOfBirth) {
        Member member = memberRepository.findByNameAndDateOfBirth(name, dateOfBirth).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        MemberInfoResponse response = new MemberInfoResponse();
        response.setEmail(member.getEmail());
        response.setDateOfBirth(member.getDateOfBirth());
        response.setName(member.getName());
        response.setGender(member.getGender());

        return response;
    }

    // 이메일로 검색
    @Transactional(readOnly = true)
    public MemberInfoResponse findByEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. email: " + email));

        MemberInfoResponse response = new MemberInfoResponse();
        response.setEmail(member.getEmail());
        response.setDateOfBirth(member.getDateOfBirth());
        response.setName(member.getName());
        response.setGender(member.getGender());

        return response;
    }

    // 회원탈퇴
    @Transactional
    public void deleteId(Integer id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID의 회원이 존재하지 않습니다. ID: " + id));

        memberRepository.delete(member);
    }
}
