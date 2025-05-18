package com.example.moneytrack.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transfer {

    // 계좌이체 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Integer transferId;

    // 출금 계좌
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "withdrawal_account", nullable = false)
    private Account withdrawalAccount;

    // 입금 계좌
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_account", nullable = false)
    private Account depositAccount;

    // 이체 금액
    @Column(nullable = false)
    private Long amount;

    // 이체 시각
    @Column(nullable = false)
    private LocalDateTime transferAt;


    public static Transfer create(Account withdrawalAccount, Account depositAccount, Long amount) {
        Transfer transfer = new Transfer();
        transfer.withdrawalAccount = withdrawalAccount;
        transfer.depositAccount = depositAccount;
        transfer.amount = amount;
        transfer.transferAt = LocalDateTime.now();

        return transfer;
    }
}
