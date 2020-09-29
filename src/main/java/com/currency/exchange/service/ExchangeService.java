package com.currency.exchange.service;

import com.currency.exchange.controller.model.ConvertCurrencyDto;
import com.currency.exchange.controller.model.LatestRateDto;

import java.util.Date;

public interface ExchangeService {

    LatestRateDto getExchangeRate(String base, String symbols);

    void convertCurrency(ConvertCurrencyDto convertCurrencyDto);

    void listConversions(Long transactionId, Date transactionDate);
}
