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

    @Transactional
    public MemberSignupResponse signup(MemberSignupRequest request) {

        Member member = new Member.Builder(request.getEmail(), request.getPassword(), request.getName(), request.getDateOfBirth())
                .gender(request.getGender())
                .build();

        Integer id = memberRepository.save(member).getId();

        MemberSignupResponse response = new MemberSignupResponse();
        response.setId(id);

        return response;
    }
}
