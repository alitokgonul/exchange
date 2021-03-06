package com.currency.exchange.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceCurrency;

    private String targetCurrency;

    @CreationTimestamp
    private LocalDate createdDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction")
    private List<Conversion> conversions;
}
