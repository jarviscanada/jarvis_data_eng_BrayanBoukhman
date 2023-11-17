package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.dao.PositionDAO;
import ca.jrvs.apps.jdbc.dao.QuoteDAO;
import ca.jrvs.apps.jdbc.dto.Position;
import ca.jrvs.apps.jdbc.dto.Quote;
import ca.jrvs.apps.jdbc.helper.DatabaseConnectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        Quote testQuote = TestHelper.newQuoteDTO();
        int savedQuoteId = quoteDAO.save(testQuote);
        // Create a test PositionDTO and save it.
        Position testPosition = TestHelper.newPositionDTO(testQuote);
        int savedId = positionDAO.save(testPosition);

        // Use positionDAO.findById() to retrieve the saved position by its ID.
        Optional<Position> retrievedPosition = positionDAO.findById(savedId);
        // Assert that a value was returned and that it is the correct one.
        assertTrue(retrievedPosition.isPresent());
        assertEquals(retrievedPosition.get().getId(), savedId);
    }
    @Test
    public void testFindById() {
        //Generate QuoteDTO
        Quote testQuote = TestHelper.newQuoteDTO();
        int savedQuoteId = quoteDAO.save(testQuote);
        // Create a test PositionDTO and save it.
        Position testPosition = TestHelper.newPositionDTO(testQuote);
        int savedId = positionDAO.save(testPosition);

        // Use positionDAO.findById() to retrieve the saved position by its ID.
        Optional<Position> retrievedPosition = positionDAO.findById(savedId);

        // Assert that a value was returned and that it is the correct one.
        assertTrue(retrievedPosition.isPresent());
        assertEquals(retrievedPosition.get().getId(), savedId);
    }
    @Test
    public void testFindByAllSymbol() {
        // Generate a test QuoteDTO.
        Quote testQuote = TestHelper.newQuoteDTO();
        quoteDAO.save(testQuote);

        // Create multiple test PositionDTO objects with the same symbol and save them.
        Position testPosition1 = TestHelper.newPositionDTO(testQuote);
        Position testPosition2 = TestHelper.newPositionDTO(testQuote);
        int savedId1 = positionDAO.save(testPosition1);
        testPosition1.setId(savedId1);
        int savedId2 = positionDAO.save(testPosition2);
        testPosition2.setId(savedId2);

        // Use positionDAO.findByAllSymbol() to retrieve positions with the same symbol.
        Iterable<Position> retrievedPositions = positionDAO.findByAllSymbol(testQuote.getSymbol());

        // Assert that the retrieved positions contain the test positions.
        List<Position> retrievedPositionList = new ArrayList<>();
        retrievedPositions.forEach(retrievedPositionList::add);
        assertTrue(retrievedPositionList.contains(testPosition1));
        assertTrue(retrievedPositionList.contains(testPosition2));
    }
    @Test
    public void testDeleteById() {
        //Generate QuoteDTO
        Quote testQuote = TestHelper.newQuoteDTO();
        int savedQuoteId = quoteDAO.save(testQuote);
        // Create a test PositionDTO and save it.
        Position testPosition = TestHelper.newPositionDTO(testQuote);
        int savedId = positionDAO.save(testPosition);

        // Use positionDAO.deleteById() to delete the saved position by its ID.
        positionDAO.deleteById(savedId);

        // Try to retrieve the deleted position.
        Optional<Position> deletedPosition = positionDAO.findById(savedId);

        // Assert that the deleted position is not found (Optional is empty).
        assertTrue(deletedPosition.isEmpty());
    }

}
