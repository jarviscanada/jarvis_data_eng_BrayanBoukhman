package ca.jrvs.practice.midterm.repository;

import ca.jrvs.practice.midterm.model.Currency;
import ca.jrvs.practice.midterm.model.Rate;
import ca.jrvs.practice.midterm.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;

public class DailyRateRepo {

    private Connection c;

    private static final Logger logger = LoggerFactory.getLogger(DailyRateRepo.class);


    public DailyRateRepo(Connection c) {
        this.c = c;
    }

    public Optional<Rate> getRate(Date date, String fromCode, String toCode) {
        try (PreparedStatement s = c.prepareStatement("SELECT * FROM forex.daily_rate WHERE day=?, from_symbol=?, to_symbol=?")) {
            s.setDate(1, date);
            s.setString(2,fromCode);
            s.setString(3,toCode);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Rate rate = new Rate();
                rate.setDate(rs.getDate("day"));
                rate.setFromSymbol(rs.getString("from_symbol"));
                rate.setToSymbol(rs.getString("to_symbol"));
                rate.setOpen(rs.getDouble("open"));
                rate.setOpen(rs.getDouble("high"));
                rate.setOpen(rs.getDouble("low"));
                rate.setOpen(rs.getDouble("close"));
                return Optional.of(rate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void save(Rate r) {
        try (PreparedStatement s = c.prepareStatement("INSERT INTO daily_rate (day, from_symbol, to_symbol, open, high, low, close) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);")) {
            s.setDate(1, r.getDate());
            s.setString(2,r.getFromSymbol());
            s.setString(3,r.getToSymbol());
            s.setDouble(4,r.getOpen());
            s.setDouble(5,r.getHigh());
            s.setDouble(6,r.getLow());
            s.setDouble(7,r.getClose());
            s.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
