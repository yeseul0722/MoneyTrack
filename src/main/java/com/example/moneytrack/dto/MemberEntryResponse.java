package com.example.moneytrack.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberEntryResponse {
    private Long totalBalance;
    private List<EntryDetail> entries;
}
