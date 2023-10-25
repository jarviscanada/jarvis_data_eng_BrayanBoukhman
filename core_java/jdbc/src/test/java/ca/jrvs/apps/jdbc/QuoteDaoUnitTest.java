package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.dao.QuoteDAO;
import ca.jrvs.apps.jdbc.dto.QuoteDTO;
import ca.jrvs.apps.jdbc.helper.ApiManager;
import ca.jrvs.apps.jdbc.helper.DatabaseConnectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class QuoteDaoUnitTest {

    private MockedStatic<ApiManager> mockedApiManager;
    private Connection connection;
    private QuoteDAO quoteDAO;

    @Before
    public void setup() {
        try {
            connection = DatabaseConnectionManager.getConnection();
            quoteDAO = new QuoteDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        mockedApiManager = Mockito.mockStatic(ApiManager.class);
        mockedApiManager.when(() -> ApiManager.fetchQuoteInfo(ArgumentMatchers.anyString())).thenReturn(TestHelper.newQuoteDTO());
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
        QuoteDTO testQuote = TestHelper.newQuoteDTO();
        int testID = quoteDAO.save(testQuote);

        // Use quoteDAO.findById() to retrieve the saved quote by its ID.
        Optional<QuoteDTO> retrievedQuote = quoteDAO.findById(testID);
        assertTrue(retrievedQuote.isPresent());
        assertEquals(retrievedQuote.get().getId(), testID);
    }

    @Test
    public void testFindById() {
        // Create a test QuoteDTO and save it.
        QuoteDTO testQuote = TestHelper.newQuoteDTO();
        int testID = quoteDAO.save(testQuote);

        // Use quoteDAO.findById() to retrieve the saved quote by its ID.
        Optional<QuoteDTO> retrievedQuote = quoteDAO.findById(testID);
        assertTrue(retrievedQuote.isPresent());
        assertEquals(retrievedQuote.get().getId(), testID);
    }

    @Test
    public void testFindBySymbol() {
        // Create a test QuoteDTO and save it.
        QuoteDTO testQuote = TestHelper.newQuoteDTO();
        int testID = quoteDAO.save(testQuote);

        // Use quoteDAO.findBySymbol() to retrieve the saved quote by its symbol.
        Optional<QuoteDTO> retrievedQuote = quoteDAO.findBySymbol(testQuote.getSymbol());
        assertTrue(retrievedQuote.isPresent());
        assertEquals(retrievedQuote.get().getId(), testID);
    }

    @Test
    public void testFindAll() {
        // Use quoteDAO.findAll() to retrieve all quotes from the database.
        List<QuoteDTO> allQuotes = quoteDAO.findAll();

        // Add assertions to verify the contents of allQuotes.
    }

    @Test
    public void testDeleteById() {
        // Create a test QuoteDTO and save it.
        QuoteDTO testQuote = new QuoteDTO(/* initialize with test data */);
        quoteDAO.save(testQuote);

        // Use quoteDAO.deleteById() to delete the saved quote by its ID.
        quoteDAO.deleteById(testQuote.getId());

        // Verify that the quote has been deleted by trying to retrieve it again.
        Optional<QuoteDTO> deletedQuote = quoteDAO.findById(testQuote.getId());
        // Add assertions to check that deletedQuote is empty.
    }

    @Test
    public void testDeleteAll() {
        // Use quoteDAO.deleteAll() to delete all quotes from the database.

        // Verify that the quotes have been deleted by checking if findAll() returns an empty list.
        List<QuoteDTO> allQuotes = quoteDAO.findAll();
        // Add assertions to check that allQuotes is empty.
    }

}
