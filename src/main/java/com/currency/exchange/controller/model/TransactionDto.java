package com.currency.exchange.controller.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    @ApiModelProperty(notes = "Transaction Id")
    private Long transactionId;

    @ApiModelProperty(notes = "The amount to be converted.")
    private BigDecimal amount;

}
