package com.currency.exchange.client;

import com.currency.exchange.controller.model.LatestRateDto;

public interface FixerRestClient {

    LatestRateDto getExchangeRate(String base, String symbol);
}
