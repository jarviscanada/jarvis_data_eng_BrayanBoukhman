package ca.jrvs.apps.jdbc.service;

import ca.jrvs.apps.jdbc.dao.QuoteDAO;
import ca.jrvs.apps.jdbc.dto.Quote;

import java.util.Optional;

public class QuoteService {

    private QuoteDAO dao;

    /**
     * Fetches latest quote data from endpoint
     * @param symbol
     * @return Latest quote information or empty optional if symbol not found
     */
    public Optional<Quote> fetchQuoteDataFromAPI(String symbol) {
        //TO DO
    }

}
