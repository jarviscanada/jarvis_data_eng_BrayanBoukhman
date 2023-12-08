package ca.jrvs.apps.trading.model;


import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TestConfig.class})
class QuoteJpaTest {

    @Autowired
    private QuoteJpaRepository quoteJpaRepository;

    @Test
    void saveOne() {
        // Arrange
        Quote quote = createTestQuote("MSFT");

        // Act
        Quote savedQuote = quoteJpaRepository.save(quote);

        // Assert
        assertEquals(quote, savedQuote);
    }

    @Test
    void saveAll() {
        // Arrange
        Quote quote1 = createTestQuote("AAPL");
        Quote quote2 = createTestQuote("GOOGL");
        List<Quote> quotesToSave = Arrays.asList(quote1, quote2);

        // Act
        Iterable<Quote> savedQuotes = quoteJpaRepository.saveAll(quotesToSave);

        // Assert
        assertEquals(quotesToSave, savedQuotes);
    }

    @Test
    void findAll() {
        // Act
        Iterable<Quote> allQuotes = quoteJpaRepository.findAll();

        // Assert
        assertNotNull(allQuotes);
    }

    // Helper method to create a test Quote object
    private Quote createTestQuote(String ticker) {
        Quote quote = new Quote();
        quote.setAskPrice(10d);
        quote.setAskSize(10);
        quote.setBidPrice(10.2d);
        quote.setTicker(ticker);
        quote.setLastPrice(10.1d);
        return quote;
    }
}
