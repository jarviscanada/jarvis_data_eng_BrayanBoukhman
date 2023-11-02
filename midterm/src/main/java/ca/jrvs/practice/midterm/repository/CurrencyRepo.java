package ca.jrvs.practice.midterm.repository;

import ca.jrvs.practice.midterm.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CurrencyRepo {

    private Connection c;

    private static final Logger logger = LoggerFactory.getLogger(CurrencyRepo.class);

    public CurrencyRepo(Connection c) {
        this.c = c;
    }

    public Optional<Currency> getCurrency(String code) {
        //logger.info("getCurrency has been called with (" + fromCode + ","+ toCode + ","+ date + ")");
        try (PreparedStatement s = c.prepareStatement("SELECT * FROM forex.currency WHERE symbol=?")) {
            s.setString(1, code);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Currency curr = new Currency();
                curr.setCode(code);
                curr.setName(rs.getString("name"));
                return Optional.of(curr);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

}
