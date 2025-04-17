package com.example.moneytrack.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    private LocalDate dateOfBirth;

    @Column(length = 1)
    private String gender;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    public static class Builder {

        //필수 파라미터

        private final String email;
        private final String password;
        private final String name;
        private final LocalDate dateOfBirth;


        //선택 파라미터
        private String gender;
        private List<Account> accounts = new ArrayList<>();

        public Builder(String email, String password, String name, LocalDate dateOfBirth) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder accounts(List<Account> accounts) {
            this.accounts = accounts;
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
        this.accounts = builder.accounts;
    }

    // 계좌 추가 메서드
    public void addAccount(Account account) {
        accounts.add(account);

    }
}
