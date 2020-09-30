package com.currency.exchange.service;

import com.currency.exchange.controller.model.ConversionDto;
import com.currency.exchange.controller.model.ConvertCurrencyDto;
import com.currency.exchange.controller.model.LatestRateDto;
import com.currency.exchange.controller.model.TransactionDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface ExchangeService {

    LatestRateDto getExchangeRate(String base, String symbols);

    TransactionDto convertCurrency(ConvertCurrencyDto convertCurrencyDto);

    Page<ConversionDto> listConversions(Long transactionId, LocalDate transactionDate);
}
