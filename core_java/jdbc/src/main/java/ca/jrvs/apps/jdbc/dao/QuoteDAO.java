package ca.jrvs.apps.jdbc.dao;

import ca.jrvs.apps.jdbc.dto.QuoteDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuoteDAO extends CrudDAO<QuoteDTO, Long> {
    public QuoteDAO(Connection connection) {
        super(connection);
    }
    private static final String INSERT = "INSERT INTO stock_quote_app.quote" +
            " (symbol, open, high, low, price, volume, latest_trading_day, previous_close, change, change_percent, timestamp)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String GET_ONE = "SELECT * FROM stock_quote_app.quote WHERE id = ?;";
    private static final String GET_ALL = "SELECT * FROM stock_quote_app.quote;";
    private static final String UPDATE = "UPDATE stock_quote_app.quote SET" +
            " symbol = ?, open = ?, high = ?," +
            " low = ?, price = ?, volume = ?," +
            " latest_trading_day = ?, previous_close = ?," +
            " change = ?, change_percent = ?, timestamp = ?" +
            " WHERE id = ?;";
    private static final String DELETE = "DELETE FROM stock_quote_app.quote WHERE id = ?;";
    private static final String DELETE_ALL = "DELETE FROM stock_quote_app.quote;";


    //used for CREATE and UPDATE.
    @Override
    public QuoteDTO save(QuoteDTO entity) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Optional<QuoteDTO> findById(Long id) throws IllegalArgumentException {
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE);){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return Optional.of(resultSetToQuoteDTO(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<QuoteDTO> findAll() {
        List<QuoteDTO> list = new ArrayList<>();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ALL);){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                list.add(resultSetToQuoteDTO(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void deleteById(Long id) throws IllegalArgumentException {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE);){
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE_ALL);){
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public QuoteDTO resultSetToQuoteDTO(ResultSet rs) throws SQLException {
        QuoteDTO quoteDTO = new QuoteDTO();
        quoteDTO.setId(rs.getInt("id"));
        quoteDTO.setSymbol(rs.getString("symbol"));
        quoteDTO.setOpen(rs.getDouble("open"));
        quoteDTO.setHigh(rs.getDouble("high"));
        quoteDTO.setLow(rs.getDouble("low"));
        quoteDTO.setPrice(rs.getDouble("price"));
        quoteDTO.setVolume(rs.getInt("volume"));
        quoteDTO.setLatestTradingDay(rs.getDate("latestTradingDay"));
        quoteDTO.setPreviousClose(rs.getDouble("previousClose"));
        quoteDTO.setChange(rs.getDouble("change"));
        quoteDTO.setChangePercent(rs.getString("changePercent"));
        quoteDTO.setTimestamp(rs.getTimestamp("timestamp"));

        return quoteDTO;
    }
}
