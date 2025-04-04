package com.example.moneytrack.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    private String email;
    private String password;
    private String name;
    private String gender;
    private LocalDate dateOfBirth;

    public static class Builder {

        //필수 파라미터
        private final String email;
        private final String password;
        private final String name;
        private final LocalDate dateOfBirth;

        //선택 파라미터
        private String gender;

        public Builder(String email, String password, String name, LocalDate dateOfBirth){
            this.email = email;
            this.password = password;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }

    private Member(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.name = builder.name;
        this.gender = builder.gender;
        this.dateOfBirth = builder.dateOfBirth;
    }

}
