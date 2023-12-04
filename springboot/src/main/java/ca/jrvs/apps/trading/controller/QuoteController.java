package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.helper.ResponseExceptionUtil;
import ca.jrvs.apps.trading.model.IexQuote;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/quote")
public class QuoteController {
    private Logger logger = LoggerFactory.getLogger(QuoteController.class);

    @Autowired
    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        logger.debug("Creating QuoteController");
        this.quoteService = quoteService;
    }

    @GetMapping("iex/stock/{ticker}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IexQuote getQuote(@PathVariable String ticker) {
        try {
            return quoteService.findIexQuoteByTicker(ticker);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PutMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Quote putQuote(@RequestBody Quote quote) {
        try {
            return quoteService.saveQuote(quote);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PostMapping("/tickerId/{tickerId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Quote createQuote(@PathVariable String tickerId) {
        try {
            return quoteService.saveQuote(tickerId.toUpperCase());
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @GetMapping(path = "/dailyList")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> getDailyList() {
        try {
            return quoteService.findAllQuotes();
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @GetMapping(path = "/updateMarketData")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateMarketData() {
        try {
            quoteService.updateMarketData();
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}