package com.example.moneytrack.domain;

import com.example.moneytrack.domain.enums.TransactionStatus;
import com.example.moneytrack.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tx")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tx_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TransactionType type;

    @Column(length = 255)
    private String description;

    // 멱등성/추적용
    @Column(length = 100, unique = true)
    private String correlationId;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id asc")
    @Builder.Default
    private List<Entry> entries = new ArrayList<>();

    public void addEntry(Entry e) {
        e.setTransaction(this);
        this.entries.add(e);
    }

    public static Transaction newHeader(String description, TransactionType type, String correlationId) {
        return Transaction.builder()
                .occurredAt(LocalDateTime.now())
                .status(TransactionStatus.PENDING)
                .description(description == null ? "" : description)
                .type(type)
                .correlationId(correlationId)
                .build();
    }

}
