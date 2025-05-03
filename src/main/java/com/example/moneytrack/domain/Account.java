package com.example.moneytrack.domain;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(unique = true, length = 16, nullable = false)
    private String accountNumber;
    @Column(nullable = false)
    private Integer balance;
    @Column(length = 3, nullable = false)
    private String productCode;

    public Account(Member member, String accountNumber, Integer balance, String productCode) {
        this.member = member;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.productCode = productCode;
    }

    public static Account create(Member member, String accountNumber, String productCode) {
        return new Account(member, accountNumber, 0, productCode);
    }

}
