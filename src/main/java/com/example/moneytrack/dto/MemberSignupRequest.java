package com.example.moneytrack.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MemberSignupRequest {
    private String email;
    private String password;
    private String name;
    private String gender;
    private LocalDate dateOfBirth;
}
