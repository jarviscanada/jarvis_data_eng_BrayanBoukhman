package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.dao.QuoteDAO;
import ca.jrvs.apps.jdbc.dto.QuoteDTO;
import ca.jrvs.apps.jdbc.helper.DatabaseConnectionManager;
import ca.jrvs.apps.jdbc.helper.ApiManager;
import ca.jrvs.apps.jdbc.helper.PropertyManager;

import java.sql.*;
import java.time.Instant;
import java.util.List;

public class JDBCExecutor {
    public static void main(String... args) throws SQLException {

        QuoteDAO quoteDAO = new QuoteDAO(DatabaseConnectionManager.getConnection());

        QuoteDTO quoteDTO = ApiManager.fetchQuoteInfo("GOOGL");
        assert quoteDTO != null;
        quoteDTO.setTimestamp(Timestamp.from(Instant.now()));

        quoteDAO.save(quoteDTO);
        List<QuoteDTO> list = quoteDAO.findAll();;

        for(QuoteDTO quoteDTOtmp : list) {
            System.out.println(quoteDTOtmp.getSymbol());
        }

        System.out.println(PropertyManager.getServer());
        System.out.println(PropertyManager.getDatabase());
        System.out.println(PropertyManager.getApiKey());
        System.out.println(quoteDTO.getOpen());

    }
}
