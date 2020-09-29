package com.currency.exchange.service;

import com.currency.exchange.controller.model.ConversionDto;
import com.currency.exchange.controller.model.ConvertCurrencyDto;
import com.currency.exchange.controller.model.LatestRateDto;
import com.currency.exchange.controller.model.TransactionDto;

import java.util.Date;
import java.util.List;

public interface ExchangeService {

    LatestRateDto getExchangeRate(String base, String symbols);

    TransactionDto convertCurrency(ConvertCurrencyDto convertCurrencyDto);

    List<ConversionDto> listConversions(Long transactionId, Date transactionDate);
}
