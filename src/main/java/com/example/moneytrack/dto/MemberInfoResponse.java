package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Member;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberInfoResponse {
    private final String email;
    private final String name;
    private final String gender;
    private final LocalDate dateOfBirth;

    private MemberInfoResponse(String email, String name, String gender, LocalDate dateOfBirth) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
                member.getEmail(),
                member.getName(),
                member.getGender(),
                member.getDateOfBirth()
        );
    }
}
