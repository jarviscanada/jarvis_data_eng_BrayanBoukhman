package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.dao.PositionDAO;
import ca.jrvs.apps.jdbc.dao.PositionDAO;
import ca.jrvs.apps.jdbc.dao.QuoteDAO;
import ca.jrvs.apps.jdbc.dto.PositionDTO;
import ca.jrvs.apps.jdbc.dto.QuoteDTO;
import ca.jrvs.apps.jdbc.helper.DatabaseConnectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class PositionDaoUnitTest {
    private PositionDAO positionDAO;
    private QuoteDAO quoteDAO;
    private Connection connection;

    @Before
    public void setUp() {
        // Initialize the database connection and the PositionDAO
        try {
            connection = DatabaseConnectionManager.getConnection();
            positionDAO = new PositionDAO(connection);
            quoteDAO = new QuoteDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        positionDAO.deleteAll();
        quoteDAO.deleteAll();
    }

    @After
    public void tearDown() {
        // Close the database connection
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSave() {
        //Generate QuoteDTO
        QuoteDTO testQuote = TestHelper.newQuoteDTO();
        int savedQuoteId = quoteDAO.save(testQuote);
        // Create a test PositionDTO and save it.
        PositionDTO testPosition = TestHelper.newPositionDTO(testQuote);
        int savedId = positionDAO.save(testPosition);

        // Use positionDAO.findById() to retrieve the saved position by its ID.
        Optional<PositionDTO> retrievedPosition = positionDAO.findById(savedId);
        // Assert that a value was returned and that it is the correct one.
        assertTrue(retrievedPosition.isPresent());
        assertEquals(retrievedPosition.get().getId(), savedId);
    }

}
