package com.currency.exchange.controller.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LatestRateDto {

    @ApiModelProperty(notes = "Returns true or false depending on whether or not your API request has succeeded.")
    private boolean success;

    @ApiModelProperty(notes = "Returns the exact date and time (UNIX time stamp) the given rates were collected.")
    private Long timestamp;

    @ApiModelProperty(notes = "Returns the three-letter currency code of the base currency used for this request.")
    private String base;

    private Date date;

    private Map<String, BigDecimal> rates;
}
