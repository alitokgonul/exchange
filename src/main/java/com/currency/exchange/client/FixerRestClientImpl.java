package com.currency.exchange.client;

import com.currency.exchange.controller.model.LatestRateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Service
public class FixerRestClientImpl implements FixerRestClient {

    @Value("${fixer.key}")
    private String accessKey;

    @Value("${fixer.url}")
    private String url;

    private final RestTemplate restTemplate;

    public LatestRateDto getExchangeRate(String base, String symbols) {
        log.info("fixer latest api is called");
        LatestRateDto latestRateDto = null;
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "latest")
                    .queryParam("access_key", accessKey)
                    .queryParam("base", base)
                    .queryParam("symbols", symbols);

            latestRateDto = restTemplate.getForObject(builder.toUriString(), LatestRateDto.class);
        } catch (Exception e) {
            log.error("error while getting fixer latest api with base : {} and symbols :", base, symbols);
        }


        return latestRateDto;
    }

}
