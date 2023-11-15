package ca.jrvs.apps.jdbc;
import ca.jrvs.apps.jdbc.dao.PositionDAO;
import ca.jrvs.apps.jdbc.dao.QuoteDAO;
import ca.jrvs.apps.jdbc.dto.Quote;
import ca.jrvs.apps.jdbc.helper.DatabaseConnectionManager;
import ca.jrvs.apps.jdbc.service.PositionService;
import ca.jrvs.apps.jdbc.service.QuoteService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class QuoteServiceIntTest {
    QuoteDAO quoteDAO;
    PositionDAO positionDAO;
    QuoteService quoteService;
    PositionService positionService;
    @Before
    public void setUp() {

    }

    @Test
    public void createQuote() throws SQLException {

        Connection c = DatabaseConnectionManager.getConnection();
        quoteDAO = new QuoteDAO(c);
        positionDAO = new PositionDAO(c);
        quoteService = new QuoteService(quoteDAO);
        positionService = new PositionService(positionDAO);

        positionDAO.deleteAll();
        quoteDAO.deleteAll();

        Quote quote = TestHelper.newQuoteDTO();
        quoteService.saveQuote(quote);

        Optional<Quote> savedQuote = quoteDAO.findBySymbol(quote.getSymbol());
        assertTrue(savedQuote.isPresent());
    }

}
