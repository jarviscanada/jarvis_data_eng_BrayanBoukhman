package ca.jrvs.apps.jdbc.service;

import ca.jrvs.apps.jdbc.dao.QuoteDAO;
import ca.jrvs.apps.jdbc.dto.Quote;
import ca.jrvs.apps.jdbc.helper.ApiManager;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class QuoteService {

    private final QuoteDAO dao;

    public Optional<Quote> fetchQuoteDataFromAPI(String symbol) {
        return Optional.ofNullable(ApiManager.fetchQuoteInfo(symbol));
    }
    public QuoteService(QuoteDAO dao) {
        this.dao = dao;
    }
    public void saveQuote(Quote quote) {
        quote.setTimestamp(Timestamp.from(Instant.now()));
        Integer id = dao.save(quote);
        quote.setId(id);
    }

}
