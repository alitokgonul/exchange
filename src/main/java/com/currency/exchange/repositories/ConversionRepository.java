package com.currency.exchange.repositories;

import com.currency.exchange.entity.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ConversionRepository extends JpaRepository<Conversion, Long> {

    Page<Conversion> findByTransaction_Id(Long transactionId, Pageable pageable);

    Page<Conversion> findByCreatedDate(LocalDate date, Pageable pageable);

    Page<Conversion> findByTransaction_IdAndCreatedDate(Long transactionId, LocalDate date, Pageable pageable);

}
