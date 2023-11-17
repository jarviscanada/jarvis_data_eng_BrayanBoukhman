package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.controller.StockQuoteController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        StockQuoteController stockQuoteController = new StockQuoteController();


        try {
            stockQuoteController.initClient();
            stockQuoteController.run();
        } catch (SQLException e) {
            logger.error("Could not open connection to database - " + e);
            throw new RuntimeException(e);
        }
    }
}
