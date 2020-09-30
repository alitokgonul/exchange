package com.currency.exchange.controller.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionDto {

    @ApiModelProperty(notes = "Transaction id")
    private Long transactionId;

    @ApiModelProperty(notes = "Returns the three-letter currency code of the source currency used for this request.")
    private String sourceCurrency;

    @ApiModelProperty(notes = "Returns the three-letter currency code of the target currency used for this request.")
    private String targetCurrency;

    @ApiModelProperty(notes = "Returns exchange rate data for the currency you have requested.")
    private BigDecimal exchangeRate;

    @ApiModelProperty(notes = "The amount to be converted.")
    private BigDecimal amount;

    @ApiModelProperty(notes = "Transaction date")
    private LocalDate transationDate;

}
