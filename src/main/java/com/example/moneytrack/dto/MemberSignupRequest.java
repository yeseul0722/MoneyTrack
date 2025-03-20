package com.example.moneytrack.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberSignupRequest {
    private String email;
    private String password;
    private String name;
    private String gender;
    private LocalDate dateOfBirth;
}
