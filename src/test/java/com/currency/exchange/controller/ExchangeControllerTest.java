package com.currency.exchange.controller;

import com.currency.exchange.controller.model.ConversionDto;
import com.currency.exchange.controller.model.ConvertCurrencyDto;
import com.currency.exchange.controller.model.LatestRateDto;
import com.currency.exchange.controller.model.TransactionDto;
import com.currency.exchange.service.ExchangeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ExchangeControllerTest {

    private static final String EXCHANGE_ENDPOINT = "/api/v1/exchange";

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @Mock
    private ExchangeService exchangeService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ExchangeController(exchangeService))
                .build();
    }

    @Test
    public void getExchangeRate() throws Exception {
        given(exchangeService.getExchangeRate("EUR", "TRY")).willReturn(createLatestRateDto());

        MvcResult mvcResult = mockMvc.perform(get(EXCHANGE_ENDPOINT + "?base=EUR&symbols=TRY")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        LatestRateDto dto = objectMapper.readValue(contentAsString, LatestRateDto.class);

        assertEquals(dto.getBase(), "EUR");
        assertTrue(dto.isSuccess());
        assertEquals(dto.getTimestamp(), 1601402045L);
    }

    @Test
    public void convert() throws Exception {
        given(exchangeService.convertCurrency(any())).willReturn(createTransactionDto());

        ConvertCurrencyDto request = createConvertCurrencyDto();
        String createRequestStr = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(post(EXCHANGE_ENDPOINT + "/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequestStr))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        TransactionDto dto = objectMapper.readValue(contentAsString, TransactionDto.class);

        assertEquals(dto.getAmount(), new BigDecimal(1.222));
        assertEquals(dto.getTransactionId(), 1L);
    }

    @Test
    public void listConversions() throws Exception {
        given(exchangeService.listConversions(any(), any(), any(), any())).willReturn(createConversionPage());

        mockMvc.perform(get(EXCHANGE_ENDPOINT + "/list-conversions?transactionId=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void listConversions_bad_request() throws Exception {
        mockMvc.perform(get(EXCHANGE_ENDPOINT + "/list-conversions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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

    private TransactionDto createTransactionDto() {
        TransactionDto dto = new TransactionDto();
        dto.setAmount(new BigDecimal(1.222));
        dto.setTransactionId(1L);

        return dto;
    }

    private ConvertCurrencyDto createConvertCurrencyDto() {
        ConvertCurrencyDto dto = new ConvertCurrencyDto();
        dto.setAmount(new BigDecimal(11.2));
        dto.setFrom("EUR");
        dto.setTo("TRY");

        return dto;
    }

    private Page<ConversionDto> createConversionPage() {
        ConversionDto dto = new ConversionDto();
        dto.setExchangeRate(new BigDecimal(1.1));
        dto.setAmount(new BigDecimal(2));
        dto.setTransactionId(2L);

        return new PageImpl<>(Collections.singletonList(dto));
    }
}