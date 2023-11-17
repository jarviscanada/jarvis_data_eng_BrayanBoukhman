package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.dao.PositionDAO;
import ca.jrvs.apps.jdbc.dao.QuoteDAO;
import ca.jrvs.apps.jdbc.dto.Quote;
import ca.jrvs.apps.jdbc.helper.ApiManager;
import ca.jrvs.apps.jdbc.helper.DatabaseConnectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class QuoteDaoUnitTest {

    private MockedStatic<ApiManager> mockedApiManager;
    private Connection connection;
    private QuoteDAO quoteDAO;
    private PositionDAO positionDAO;

    @Before
    public void setup() {
        try {
            connection = DatabaseConnectionManager.getConnection();
            positionDAO = new PositionDAO(connection);
            quoteDAO = new QuoteDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        positionDAO.deleteAll();
        quoteDAO.deleteAll();

        mockedApiManager = Mockito.mockStatic(ApiManager.class);
        mockedApiManager.when(() -> ApiManager.fetchQuoteInfo(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            String symbol = invocation.getArgument(0);
            return TestHelper.newQuoteDTO(symbol);
        });
    }

    @After
    public void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mockedApiManager.close();
    }

    @Test
    public void testSave() {
        // Create a test QuoteDTO and save it.
        Quote testQuote = ApiManager.fetchQuoteInfo("MSFT");
        assert testQuote != null;
        int testID = quoteDAO.save(testQuote);

        // Use quoteDAO.findById() to retrieve the saved quote by its ID.
        Optional<Quote> retrievedQuote = quoteDAO.findById(testID);
        assertTrue(retrievedQuote.isPresent());
        assertEquals(retrievedQuote.get().getId(), testID);
    }

    @Test
    public void testFindById() {
        // Create a test QuoteDTO and save it.
        Quote testQuote = ApiManager.fetchQuoteInfo("AAPL");
        assert testQuote != null;
        int testID = quoteDAO.save(testQuote);

        // Use quoteDAO.findById() to retrieve the saved quote by its ID.
        Optional<Quote> retrievedQuote = quoteDAO.findById(testID);
        assertTrue(retrievedQuote.isPresent());
        assertEquals(retrievedQuote.get().getId(), testID);
    }

    @Test
    public void testFindBySymbol() {
        // Create a test QuoteDTO and save it.
        Quote testQuote = ApiManager.fetchQuoteInfo("GOOGL");
        assert testQuote != null;
        int testID = quoteDAO.save(testQuote);

        // Use quoteDAO.findBySymbol() to retrieve the saved quote by its symbol.
        Optional<Quote> retrievedQuote = quoteDAO.findBySymbol(testQuote.getSymbol());
        assertTrue(retrievedQuote.isPresent());
        assertEquals(retrievedQuote.get().getId(), testID);
    }

    @Test
    public void testFindAll() {
        //first remove all values
        quoteDAO.deleteAll();

        //add a bunch of values to the database
        quoteDAO.save(Objects.requireNonNull(ApiManager.fetchQuoteInfo("GOOGL")));
        quoteDAO.save(Objects.requireNonNull(ApiManager.fetchQuoteInfo("GOOG")));
        quoteDAO.save(Objects.requireNonNull(ApiManager.fetchQuoteInfo("PYPL")));
        //these 2 share the same Symbol so the total number of added Quote's is 4
        quoteDAO.save(Objects.requireNonNull(ApiManager.fetchQuoteInfo("PFE")));
        quoteDAO.save(Objects.requireNonNull(ApiManager.fetchQuoteInfo("PFE")));

        // Use quoteDAO.findAll() to retrieve all quotes from the database.
        List<Quote> allQuotes = quoteDAO.findAll();

        // Add assertions to verify the contents of allQuotes.
        assertEquals(4, allQuotes.size());
    }

    @Test
    public void testDeleteById() {
        // Create a test QuoteDTO and save it.
        Quote testQuote = ApiManager.fetchQuoteInfo("GOOGL");
        assert testQuote != null;
        int testID = quoteDAO.save(testQuote);

        // Use quoteDAO.deleteById() to delete the saved quote by its ID.
        quoteDAO.deleteById(testID);

        // Verify that the quote has been deleted by trying to retrieve it again.
        Optional<Quote> deletedQuote = quoteDAO.findById(testID);
        // Add assertions to check that deletedQuote is empty.
        assertTrue(deletedQuote.isEmpty());
    }

    @Test
    public void testDeleteAll() {
        // Add a bunch of values to the database
        quoteDAO.save(Objects.requireNonNull(ApiManager.fetchQuoteInfo("GOOGL")));
        quoteDAO.save(Objects.requireNonNull(ApiManager.fetchQuoteInfo("GOOG")));
        quoteDAO.save(Objects.requireNonNull(ApiManager.fetchQuoteInfo("PYPL")));
        // Use quoteDAO.deleteAll() to delete all quotes from the database
        quoteDAO.deleteAll();

        // Verify that the quotes have been deleted by checking if findAll() returns an empty list
        List<Quote> allQuotes = quoteDAO.findAll();

        // Add an assertion to check that allQuotes is empty
        assertTrue(allQuotes.isEmpty());
    }

}
