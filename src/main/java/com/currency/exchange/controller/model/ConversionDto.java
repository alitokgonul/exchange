package com.currency.exchange.controller.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionDto {

    @ApiModelProperty(notes = "")
    private Long transactionId;

    @ApiModelProperty(notes = "")
    private String sourceCurrency;

    @ApiModelProperty(notes = "")
    private String targetCurrency;

    @ApiModelProperty(notes = "")
    private BigDecimal sourceAmount;

    @ApiModelProperty(notes = "")
    private BigDecimal targetAmount;

    @ApiModelProperty(notes = "")
    private String transationDate;

}
