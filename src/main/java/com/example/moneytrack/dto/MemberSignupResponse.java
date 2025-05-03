package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Member;
import lombok.Getter;

@Getter
public class MemberSignupResponse {
    private final Integer id;

    private MemberSignupResponse(Integer id) {
        this.id = id;
    }

    public static MemberSignupResponse from(Member member) {
        return new MemberSignupResponse(
                member.getId()
        );
    }
}
