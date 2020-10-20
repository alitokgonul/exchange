package com.currency.exchange.repositories;

import com.currency.exchange.entity.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ConversionRepository extends JpaRepository<Conversion, Long> {

    @Query("SELECT c FROM Conversion c WHERE (:transactionId is null or :transactionId = c.transaction.id) AND (:date is null or :date = c.createdDate)")
    Page<Conversion> filterConversions(@Param("transactionId") Long transactionId, @Param("date")LocalDate date, Pageable pageable);

}
