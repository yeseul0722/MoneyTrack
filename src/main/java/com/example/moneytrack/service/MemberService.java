package com.example.moneytrack.service;

import com.example.moneytrack.domain.Member;
import com.example.moneytrack.dto.MemberSignupRequest;
import com.example.moneytrack.dto.MemberSignupResponse;
import com.example.moneytrack.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private void vaildateDuplicateMember(Member member) {
        memberRepository.findByEmail(member.getEmail()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    // 회원탈퇴
    @Transactional
    public void deleteId(Integer id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID의 회원이 존재하지 않습니다. ID: " + id));

        memberRepository.delete(member);
    }
}
