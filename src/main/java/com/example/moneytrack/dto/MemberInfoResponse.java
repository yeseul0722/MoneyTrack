package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Account;
import com.example.moneytrack.domain.Member;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberInfoResponse {
    private final String email;
    private final String name;
    private final String gender;
    private final LocalDate dateOfBirth;
    private final List<String> accountNumbers;

    private MemberInfoResponse(String email, String name, String gender, LocalDate dateOfBirth, List<String> accountNumbers) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.accountNumbers = accountNumbers;
    }

    public static MemberInfoResponse from(Member member) {
        List<String> accountNumbers = member.getAccounts().stream()
                .map(Account::getAccountNumber)
                .collect(Collectors.toList());

        return new MemberInfoResponse(
                member.getEmail(),
                member.getName(),
                member.getGender(),
                member.getDateOfBirth(),
                accountNumbers
        );
    }
}
