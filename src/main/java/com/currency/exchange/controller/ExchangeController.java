package com.currency.exchange.controller;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exchange")
@Api(tags = {"Exchange"})
@SwaggerDefinition(tags = {
        @Tag(name = "Exchange", description = "Exchange currency pairs and conversion")
})
public class ExchangeController {

    @ApiOperation(value = "Exchange rate")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The exchange rate is successfully retrieved."),
            @ApiResponse(code = 404, message = "Accessing the resource you were trying to reach is not found"),
            @ApiResponse(code = 424, message = "The request to fixer api failed."),
            @ApiResponse(code = 500, message = "The exchange rate could not be retrieved.")
    })
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")

    public ResponseEntity getMethod(@RequestParam String base,
                                    @RequestParam String symbol) {

        return null;
    }

}
