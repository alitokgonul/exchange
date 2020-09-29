package com.currency.exchange.service;

import com.currency.exchange.client.FixerRestClient;
import com.currency.exchange.controller.model.ConversionDto;
import com.currency.exchange.controller.model.ConvertCurrencyDto;
import com.currency.exchange.controller.model.LatestRateDto;
import com.currency.exchange.controller.model.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final FixerRestClient fixerRestClient;

    @Override
    public LatestRateDto getExchangeRate(String base, String symbols) {
        return fixerRestClient.getLatest(base, symbols);
    }

    @Override
    public TransactionDto convertCurrency(ConvertCurrencyDto convertCurrencyDto) {
        return null;
    }

    @Override
    public List<ConversionDto> listConversions(Long transactionId, Date transactionDate) {
        return Collections.emptyList();
    }
}
