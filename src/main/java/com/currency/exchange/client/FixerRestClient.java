package com.currency.exchange.client;

import com.currency.exchange.controller.model.LatestRateDto;

public interface FixerRestClient {

    LatestRateDto getLatest(String base, String symbol);

}
