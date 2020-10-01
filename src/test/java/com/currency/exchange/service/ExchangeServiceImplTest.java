package com.currency.exchange.service;

import com.currency.exchange.client.FixerRestClient;
import com.currency.exchange.controller.model.ConversionDto;
import com.currency.exchange.controller.model.ConvertCurrencyDto;
import com.currency.exchange.controller.model.LatestRateDto;
import com.currency.exchange.controller.model.TransactionDto;
import com.currency.exchange.entity.Conversion;
import com.currency.exchange.entity.Transaction;
import com.currency.exchange.repositories.ConversionRepository;
import com.currency.exchange.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceImplTest {

    @Mock
    private ConversionRepository conversionRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private FixerRestClient fixerRestClient;

    @InjectMocks
    private ExchangeServiceImpl exchangeService;


    @Test
    void getExchangeRate() {
        given(fixerRestClient.getLatest(any(), any())).willReturn(createLatestRateDto());

        LatestRateDto dto = exchangeService.getExchangeRate("EUR", "TRY");

        assertEquals(dto.getBase(), "EUR");
        assertTrue(dto.isSuccess());
        assertEquals(dto.getTimestamp(), 1601402045L);
    }

    @Test
    void convertCurrency_null_transaction() {
        given(fixerRestClient.getLatest(any(), any())).willReturn(createLatestRateDto());
        given(transactionRepository.findBySourceCurrencyAndTargetCurrencyAndAndCreatedDate(any(), any(), any())).willReturn(null);
        given(conversionRepository.save(any())).willReturn(createConversion());

        TransactionDto dto = exchangeService.convertCurrency(createConvertCurrencyDto());

        assertEquals(dto.getTransactionId(), 1L);
    }

    @Test
    void convertCurrency_not_null_transaction() {
        given(fixerRestClient.getLatest(any(), any())).willReturn(createLatestRateDto());
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        given(transactionRepository.findBySourceCurrencyAndTargetCurrencyAndAndCreatedDate(any(), any(), any())).willReturn(transaction);
        given(conversionRepository.save(any())).willReturn(createConversion());

        TransactionDto dto = exchangeService.convertCurrency(createConvertCurrencyDto());

        assertEquals(dto.getTransactionId(), 1L);
    }

    @Test
    void convertCurrency() {
        given(fixerRestClient.getLatest(any(), any())).willReturn(createLatestRateDto());
        given(transactionRepository.findBySourceCurrencyAndTargetCurrencyAndAndCreatedDate(any(), any(), any())).willReturn(null);
        given(conversionRepository.save(any())).willReturn(createConversion());

        TransactionDto dto = exchangeService.convertCurrency(createConvertCurrencyDto());

        assertEquals(dto.getTransactionId(), 1L);
    }

    @Test
    void convertCurrency_exception() {
        LatestRateDto dto = createLatestRateDto();
        dto.setRates(null);

        given(fixerRestClient.getLatest(any(), any())).willReturn(dto);

        try {

            exchangeService.convertCurrency(createConvertCurrencyDto());
            fail("Exception not thrown");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "please check source and target currency");
        }
    }

    @Test
    void listConversions_date_id() {
        given(conversionRepository.findByTransaction_IdAndCreatedDate(any(), any(), any())).willReturn(createConversionPage());

        Page<ConversionDto> conversionDtoPage = exchangeService.listConversions(1L, LocalDate.now(),0, 10);

        assertEquals(conversionDtoPage.getTotalElements(), 1);
        assertEquals(conversionDtoPage.getContent().get(0).getTransactionId(), 1L);
    }

    @Test
    void listConversions_id() {
        given(conversionRepository.findByTransaction_Id(any(), any())).willReturn(createConversionPage());

        Page<ConversionDto> conversionDtoPage = exchangeService.listConversions(1L, null,0, 10);

        assertEquals(conversionDtoPage.getTotalElements(), 1);
        assertEquals(conversionDtoPage.getContent().get(0).getTransactionId(), 1L);
    }

    @Test
    void listConversions_date() {
        given(conversionRepository.findByCreatedDate(any(), any())).willReturn(createConversionPage());

        Page<ConversionDto> conversionDtoPage = exchangeService.listConversions(null, LocalDate.now(),0, 10);

        assertEquals(conversionDtoPage.getTotalElements(), 1);
        assertEquals(conversionDtoPage.getContent().get(0).getTransactionId(), 1L);
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

    private Conversion createConversion() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);

        Conversion conversion = new Conversion();
        conversion.setExchangeRate(new BigDecimal(12.2));
        conversion.setAmount(new BigDecimal(2));
        conversion.setTransaction(transaction);

        return conversion;
    }

    private ConvertCurrencyDto createConvertCurrencyDto() {
        ConvertCurrencyDto convertCurrencyDto = new ConvertCurrencyDto();
        convertCurrencyDto.setTo("TRY");
        convertCurrencyDto.setFrom("USD");

        return convertCurrencyDto;
    }

    private Page<Conversion> createConversionPage() {

        return new PageImpl<>(Collections.singletonList(createConversion()));
    }
}