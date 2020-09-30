package com.currency.exchange.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(exclude = "conversions")
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private String sourceCurrency;

    private String targetCurrency;

    @CreationTimestamp
    private LocalDate createdDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction")
    private List<Conversion> conversions;
}
