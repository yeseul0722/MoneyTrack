package com.example.moneytrack.domain;

import com.example.moneytrack.domain.enums.Direction;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "entry",
        indexes = {
            @Index(name = "ix_entry_account_time", columnList = "account_id, occurredAt desc, entry_id desc"),
            @Index(name = "ix_entry_time", columnList = "occurredAt desc, entry_id")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tx_id")
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Direction direction;    // DEBIT/ CREDIT

    @Column(nullable = false)
    private Long amount;

    // 조회 최적화를 위해 라인에도 발생시각 복제 (헤더와 동일값)
    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @Column(length = 255)
    private String memo;

    public static Entry of(Account account, Direction dir, Long amount, LocalDateTime occurredAt, String memo) {
        return Entry.builder()
                .account(account)
                .direction(dir)
                .amount(amount)
                .occurredAt(occurredAt)
                .memo(memo == null ? "" : memo)
                .build();
    }
}
