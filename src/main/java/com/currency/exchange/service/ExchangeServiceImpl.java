package com.currency.exchange.service;

import com.currency.exchange.client.FixerRestClient;
import com.currency.exchange.controller.model.ConvertCurrencyDto;
import com.currency.exchange.controller.model.LatestRateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final FixerRestClient fixerRestClient;

    @Override
    public LatestRateDto getExchangeRate(String base, String symbols) {
        return fixerRestClient.getLatest(base, symbols);
    }

    @Override
    public void convertCurrency(ConvertCurrencyDto convertCurrencyDto) {

    }

    @Override
    public void listConversions(Long transactionId, Date transactionDate) {

    }
}
