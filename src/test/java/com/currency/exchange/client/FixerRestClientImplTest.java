package com.currency.exchange.client;

import com.currency.exchange.controller.model.LatestRateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FixerRestClientImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FixerRestClientImpl fixerRestClient;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(fixerRestClient, "url", "http://data.fixer.io/api/");
        ReflectionTestUtils.setField(fixerRestClient, "accessKey", "324234");
    }

    @Test
    void getLatest() {
        given(restTemplate.getForObject("http://data.fixer.io/api/latest?access_key=324234&base=EUR&symbols=USD", LatestRateDto.class)).willReturn(createLatestRateDto());

        LatestRateDto dto = fixerRestClient.getLatest("EUR", "USD");

        assertEquals(dto.getBase(), "EUR");
    }

    private LatestRateDto createLatestRateDto() {
        LatestRateDto dto = new LatestRateDto();
        dto.setBase("EUR");
        dto.setDate(new Date());
        dto.setSuccess(true);
        dto.setTimestamp(1601402045L);
        dto.setRates(Map.of("TRY", new BigDecimal(9.196264)));

        return dto;
    }
}