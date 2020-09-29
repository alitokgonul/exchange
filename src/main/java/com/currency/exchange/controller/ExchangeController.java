package com.currency.exchange.controller;

import com.currency.exchange.controller.model.ConvertCurrencyDto;
import com.currency.exchange.controller.model.LatestRateDto;
import com.currency.exchange.service.ExchangeService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

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

    public ResponseEntity<LatestRateDto> getMethod(@RequestParam String base,
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
    public ResponseEntity convert(@RequestBody @Valid ConvertCurrencyDto dto) {
        exchangeService.convertCurrency(dto);
        return ResponseEntity.ok(null);
    }

    @ApiOperation(value = "List Conversions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The conversions are successfully retrieved."),
            @ApiResponse(code = 404, message = "Accessing the resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "The conversions could not be retrieved.")
    })
    @RequestMapping(value = "list-conversions", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity listConversions(@RequestParam Long transactionId,
                                          @RequestParam Date transactionDate) {

        exchangeService.listConversions(transactionId, transactionDate);
        return ResponseEntity.ok(null);
    }

}
