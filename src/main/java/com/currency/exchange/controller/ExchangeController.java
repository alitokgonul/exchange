package com.currency.exchange.controller;

import com.currency.exchange.controller.model.ConversionDto;
import com.currency.exchange.controller.model.ConvertCurrencyDto;
import com.currency.exchange.controller.model.LatestRateDto;
import com.currency.exchange.exception.BadRequestException;
import com.currency.exchange.service.ExchangeService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exchange")
@Api(tags = {"Exchange"})
@SwaggerDefinition(tags = {
        @Tag(name = "Exchange", description = "Exchange currency pairs and conversion")
})
public class ExchangeController {

    private final ExchangeService exchangeService;

    @ApiOperation(value = "Exchange rate")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The exchange rate is successfully retrieved."),
            @ApiResponse(code = 404, message = "Accessing the resource you were trying to reach is not found"),
            @ApiResponse(code = 424, message = "The request to fixer api failed."),
            @ApiResponse(code = 500, message = "The exchange rate could not be retrieved.")
    })
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")

    public ResponseEntity<LatestRateDto> getExchangeRate(@RequestParam String base,
                                                         @RequestParam String symbols) {

        return ResponseEntity.ok(exchangeService.getExchangeRate(base, symbols));
    }

    @ApiOperation(value = "Convert currencies")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The exchange rate is successfully retrieved."),
            @ApiResponse(code = 404, message = "Accessing the resource you were trying to reach is not found"),
            @ApiResponse(code = 424, message = "The request to fixer api failed."),
            @ApiResponse(code = 500, message = "Currencies could not be converted.")
    })
    @RequestMapping(value = "convert", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity convert(@RequestBody @Valid ConvertCurrencyDto convertCurrencyDto) {
        return ResponseEntity.ok(exchangeService.convertCurrency(convertCurrencyDto));
    }

    @ApiOperation(value = "List Conversions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The conversions are successfully retrieved."),
            @ApiResponse(code = 404, message = "Accessing the resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "The conversions could not be retrieved.")
    })
    @RequestMapping(value = "list-conversions", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<ConversionDto>> listConversions(@RequestParam(required = false) Long transactionId,
                                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate transactionDate) {

        if (transactionId == null && transactionDate == null) {
            throw new BadRequestException("at least one of the inputs shall be provided.");
        }

        return ResponseEntity.ok(exchangeService.listConversions(transactionId, transactionDate));
    }

}
