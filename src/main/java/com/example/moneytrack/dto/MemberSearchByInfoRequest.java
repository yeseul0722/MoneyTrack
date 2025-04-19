package com.example.moneytrack.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MemberSearchByInfoRequest {
    private String name;
    private LocalDate dateOfBirth;
}
