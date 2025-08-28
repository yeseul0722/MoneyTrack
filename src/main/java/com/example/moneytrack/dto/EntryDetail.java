package com.example.moneytrack.dto;

import com.example.moneytrack.domain.Entry;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EntryDetail {
    private Long entryId;
    private Long amount;
    private String direction;
    private String accountNumber;
    private LocalDateTime occurredAt;
    private String memo;

    public static EntryDetail from(Entry entry) {
        return EntryDetail.builder()
                .entryId(entry.getId())
                .amount(entry.getAmount())
                .direction(entry.getDirection().name())
                .accountNumber(entry.getAccount().getAccountNumber())
                .occurredAt(entry.getOccurredAt())
                .memo(entry.getMemo())
                .build();
    }
}
