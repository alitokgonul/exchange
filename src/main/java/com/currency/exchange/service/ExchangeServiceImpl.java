package com.currency.exchange.service;

import com.currency.exchange.client.FixerRestClient;
import com.currency.exchange.controller.model.ConversionDto;
import com.currency.exchange.controller.model.ConvertCurrencyDto;
import com.currency.exchange.controller.model.LatestRateDto;
import com.currency.exchange.controller.model.TransactionDto;
import com.currency.exchange.entity.Conversion;
import com.currency.exchange.entity.Transaction;
import com.currency.exchange.exception.BadRequestException;
import com.currency.exchange.repositories.ConversionRepository;
import com.currency.exchange.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final FixerRestClient fixerRestClient;
    private final TransactionRepository transactionRepository;
    private final ConversionRepository conversionRepository;

    @Override
    public LatestRateDto getExchangeRate(String base, String symbols) {
        return fixerRestClient.getLatest(base, symbols);
    }

    @Override
    public TransactionDto convertCurrency(ConvertCurrencyDto convertCurrencyDto) {
        LatestRateDto latestRateDto = fixerRestClient.getLatest(convertCurrencyDto.getFrom(), convertCurrencyDto.getTo());

        if (CollectionUtils.isEmpty(latestRateDto.getRates())) {
            throw new BadRequestException("please check source and target currency");
        }

        BigDecimal currencyRate = latestRateDto.getRates().get(convertCurrencyDto.getTo());

        Transaction transaction = transactionRepository.findBySourceCurrencyAndTargetCurrencyAndAndCreatedDate(
                convertCurrencyDto.getFrom(), convertCurrencyDto.getTo(), LocalDate.now());

        Conversion conversion = new Conversion();
        conversion.setExchangeRate(currencyRate);
        conversion.setAmount(convertCurrencyDto.getAmount());

        if (transaction == null) {
            Transaction newTransaction = new Transaction();
            newTransaction.setSourceCurrency(convertCurrencyDto.getFrom());
            newTransaction.setTargetCurrency(convertCurrencyDto.getTo());

            conversion.setTransaction(newTransaction);
        } else {
            conversion.setTransaction(transaction);
        }

        Conversion savedConversion = conversionRepository.save(conversion);

        return new TransactionDto(savedConversion.getTransaction().getId(), currencyRate.multiply(savedConversion.getAmount()));
    }

    @Override
    public Page<ConversionDto> listConversions(Long transactionId, LocalDate transactionDate, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Conversion> conversionPage;

        conversionPage = conversionRepository.filterConversions(transactionId, transactionDate, pageable);

        return conversionPage.map(this::conversionToDto);
    }

    private ConversionDto conversionToDto(Conversion conversion) {
        ConversionDto dto = new ConversionDto();
        dto.setTransactionId(conversion.getTransaction().getId());
        dto.setExchangeRate(conversion.getExchangeRate());
        dto.setTransationDate(conversion.getCreatedDate());
        dto.setSourceCurrency(conversion.getTransaction().getSourceCurrency());
        dto.setTargetCurrency(conversion.getTransaction().getTargetCurrency());
        dto.setAmount(conversion.getAmount());

        return dto;
    }
}
