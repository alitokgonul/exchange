package com.currency.exchange.repositories;

import com.currency.exchange.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findBySourceCurrencyAndTargetCurrencyAndAndCreatedDate(String sourceCurrency, String targetCurrency, LocalDate date);

}
