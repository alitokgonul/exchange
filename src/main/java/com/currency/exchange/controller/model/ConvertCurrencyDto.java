package com.currency.exchange.controller.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvertCurrencyDto {

    @ApiModelProperty(notes = "The three-letter currency code of the currency you would like to convert from.")
    @Size(max = 3, min = 3)
    @NotBlank
    private String from;

    @ApiModelProperty(notes = "The three-letter currency code of the currency you would like to convert to.")
    @Size(max = 3, min = 3)
    @NotBlank
    private String to;

    @ApiModelProperty(notes = "The amount to be converted.")
    @DecimalMin("0.0")
    @NotNull
    private BigDecimal amount;

}