package ca.jrvs.apps.jdbc.service;

import ca.jrvs.apps.jdbc.Main;
import ca.jrvs.apps.jdbc.dao.QuoteDAO;
import ca.jrvs.apps.jdbc.dto.Quote;
import ca.jrvs.apps.jdbc.helper.ApiManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class QuoteService {

    private final QuoteDAO dao;

    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    public Optional<Quote> fetchQuoteDataFromAPI(String symbol) {
        return Optional.ofNullable(ApiManager.fetchQuoteInfo(symbol));
    }
    public QuoteService(QuoteDAO dao) {
        this.dao = dao;
    }
    public void saveQuote(Quote quote) {
        logger.info("saving quote with symbol: " + quote.getSymbol());
        quote.setTimestamp(Timestamp.from(Instant.now()));
        Integer id = dao.save(quote);
        quote.setId(id);
    }

}
