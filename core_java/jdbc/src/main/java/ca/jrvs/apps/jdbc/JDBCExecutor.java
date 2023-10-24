package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.dto.QuoteDTO;
import ca.jrvs.apps.jdbc.helper.QuoteHttpHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {
    public static void main(String... args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(
                "localhost", "postgres", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM stock_quote_app.position");

            while(resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper("bb2c8e4681mshff4fdc7648ea8fcp1b131ajsn7b26c7aefd26");
        QuoteDTO quoteDTO = quoteHttpHelper.fetchQuoteInfo("MSFT");

        System.out.println(quoteDTO.getOpen());

    }
}
