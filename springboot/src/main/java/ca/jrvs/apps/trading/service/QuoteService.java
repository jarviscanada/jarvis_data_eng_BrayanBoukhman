package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.QuoteJpaRepository;
import ca.jrvs.apps.trading.helper.MarketDataHttpHelper;
import ca.jrvs.apps.trading.model.IexQuote;
import ca.jrvs.apps.trading.model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QuoteService {
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private final MarketDataHttpHelper marketDataHttpHelper;
    private QuoteJpaRepository quoteJpaRepository;

    @Autowired
    public QuoteService(MarketDataHttpHelper marketDataHttpHelper, QuoteJpaRepository quoteJpaRepository) {
        this.marketDataHttpHelper = marketDataHttpHelper;
        this.quoteJpaRepository = quoteJpaRepository;
    }

    public QuoteService(MarketDataHttpHelper marketDataHttpHelper) {
        logger.debug("Creating QuoteService");
        this.marketDataHttpHelper = marketDataHttpHelper;
    }
    /**
     * Update quote table against IEX source
     *
     * - get all quotes from the db
     * - for each ticker get IexQuote
     * - convert IexQuote to Quote entity
     * - persist quote to db
     *
     * @throws IllegalArgumentException for invalid input
     */
    public void updateMarketData() {

        List<Quote> quotes = quoteJpaRepository.findAll();

        for (Quote quote : quotes) {
            try {
                String ticker = quote.getTicker();
                IexQuote iexQuote = findIexQuoteByTicker(ticker);
                Quote updatedQuote = buildQuoteFromIexQuote(iexQuote);

                saveQuote(updatedQuote);

                logger.info("Successfully updated quote for ticker: {}", ticker);
            } catch (IllegalArgumentException e) {

                logger.error("Failed to update quote for ticker {}: {}", quote.getTicker(), e.getMessage());
            }
        }
    }

    /**
     * Validate (against IEX) and save given tickers to quote table
     *
     * - get IexQuote(s)
     * - convert each IexQuote to Quote entity
     * - persist the quote to db
     *
     * @param tickers
     * @return list of converted quote entities
     * @throws IllegalArgumentException if ticker is not found from IEX
     */
    public List<Quote> saveQuotes(List<String> tickers) {
        List<Quote> savedQuotes = new ArrayList<>();

        for (String ticker : tickers) {
            try {
                // Fetch IexQuote for the ticker
                IexQuote iexQuote = findIexQuoteByTicker(ticker);

                // Convert IexQuote to Quote entity
                Quote quote = buildQuoteFromIexQuote(iexQuote);

                // Save the quote to the database
                saveQuote(quote);

                // Add the saved quote to the list
                savedQuotes.add(quote);
            } catch (IllegalArgumentException e) {
                // Handle the exception if the ticker is not found from IEX
                logger.error("Failed to save quote for ticker {}: {}", ticker, e.getMessage());
            }
        }

        return savedQuotes;
    }

    /**
     * Find an IexQuote from the given ticker
     *
     * @param ticker
     * @return corresponding IexQuote object
     */
    public IexQuote findIexQuoteByTicker(String ticker) throws IllegalArgumentException  {
        logger.info("Fetching quote from IEX");
        return marketDataHttpHelper.getIexQuote(ticker).orElseThrow(() -> new IllegalArgumentException("Ticker is invalid: " + ticker));
    }

    /**
     * Update a given quote to the quote table without validation
     *
     * @param quote entity to save
     */
    public Quote saveQuote(Quote quote) {
        return quoteJpaRepository.save(quote);
    }

    /**
     * Find all quotes from the quote table
     *
     * @return a list of quotes
     */
    public List<Quote> findAllQuotes() {
        return quoteJpaRepository.findAll();
    }

    /**
     * Helper method to map an IexQuote to a Quote entity
     * Note: 'iexQuote.getLatestPrice() == null' if the stock market is closed
     * Make sure to set a default value for number field(s)
     */
    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        Quote quote = new Quote();

        // Map ticker
        if (Objects.nonNull(iexQuote.getSymbol())) {
            quote.setTicker(iexQuote.getSymbol().toString());
        } else {
            throw new IllegalArgumentException("Ticker is required");
        }

        // Map lastPrice
        if (Objects.nonNull(iexQuote.getLatestPrice())) {
            quote.setLastPrice(Double.parseDouble(iexQuote.getLatestPrice().toString()));
        } else {
            // Set a default value for lastPrice if needed
            quote.setLastPrice(0.0);
        }

        // Map bidPrice
        if (Objects.nonNull(iexQuote.getIexBidPrice())) {
            quote.setBidPrice(Double.parseDouble(iexQuote.getIexBidPrice().toString()));
        } else {
            // Set a default value for bidPrice if needed
            quote.setBidPrice(0.0);
        }

        // Map bidSize
        if (Objects.nonNull(iexQuote.getIexBidSize())) {
            quote.setBidSize(Integer.parseInt(iexQuote.getIexBidSize().toString()));
        } else {
            // Set a default value for bidSize if needed
            quote.setBidSize(0);
        }

        // Map askPrice
        if (Objects.nonNull(iexQuote.getIexAskPrice())) {
            quote.setAskPrice(Double.parseDouble(iexQuote.getIexAskPrice().toString()));
        } else {
            // Set a default value for askPrice if needed
            quote.setAskPrice(0.0);
        }

        // Map askSize
        if (Objects.nonNull(iexQuote.getIexAskSize())) {
            quote.setAskSize(Integer.parseInt(iexQuote.getIexAskSize().toString()));
        } else {
            // Set a default value for askSize if needed
            quote.setAskSize(0);
        }

        return quote;
    }
    /**
     * Helper method to validate and save a single ticker
     * Not to be confused with saveQuote(Quote quote)
     */
    public Quote saveQuote(String ticker) {
        IexQuote iexQuote = new IexQuote();
        iexQuote.setSymbol(ticker);
        return saveQuote(buildQuoteFromIexQuote(iexQuote));
    }
}
