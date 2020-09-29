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

    @ApiModelProperty(notes = "")
    private Long transactionId;

    @ApiModelProperty(notes = "")
    private BigDecimal amount;

}
