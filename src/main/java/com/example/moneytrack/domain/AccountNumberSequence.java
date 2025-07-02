package com.example.moneytrack.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "account_number_sequence")
public class AccountNumberSequence {

    @Id
    private Integer id;

    @Column(name = "current_value", nullable = false)
    private Long currentValue = 0L;


}
