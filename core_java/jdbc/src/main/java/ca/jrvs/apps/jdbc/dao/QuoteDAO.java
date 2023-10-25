package ca.jrvs.apps.jdbc.dao;

import ca.jrvs.apps.jdbc.dto.QuoteDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuoteDAO extends CrudDAO<QuoteDTO, Integer> {
    public QuoteDAO(Connection connection) {
        super(connection);
    }
    private static final String INSERT = "INSERT INTO stock_quote_app.quote" +
            " (symbol, open, high, low," +
            " price, volume," +
            " latest_trading_day," +
            " previous_close," +
            " change," +
            " change_percent," +
            " timestamp)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String GET_ONE_BY_ID = "SELECT * FROM stock_quote_app.quote WHERE id = ?;";

    private static final String GET_ONE_BY_SYMBOL = "SELECT * FROM stock_quote_app.quote WHERE symbol = ?;";
    private static final String GET_ALL = "SELECT * FROM stock_quote_app.quote;";
    private static final String UPDATE = "UPDATE stock_quote_app.quote SET" +
            " symbol = ?, open = ?, high = ?," +
            " low = ?, price = ?, volume = ?," +
            " latest_trading_day = ?, previous_close = ?," +
            " change = ?, change_percent = ?, timestamp = ?" +
            " WHERE id = ?;";
    private static final String DELETE = "DELETE FROM stock_quote_app.quote WHERE id = ?;";
    private static final String DELETE_ALL = "DELETE FROM stock_quote_app.quote;";

    @Override
    public Integer save(QuoteDTO entity) throws IllegalArgumentException {
        Optional<QuoteDTO> optional = findBySymbol(entity.getSymbol());

        String prepStatement = optional.isPresent() ? UPDATE : INSERT;

        //if it exists then update it
        try(PreparedStatement statement = this.connection.prepareStatement(prepStatement, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getSymbol());
            statement.setDouble(2, entity.getOpen());
            statement.setDouble(3, entity.getHigh());
            statement.setDouble(4, entity.getLow());
            statement.setDouble(5, entity.getPrice());
            statement.setInt(6, entity.getVolume());
            statement.setDate(7, entity.getLatestTradingDay());
            statement.setDouble(8, entity.getPreviousClose());
            statement.setDouble(9, entity.getChange());
            statement.setString(10, entity.getChangePercent());
            statement.setTimestamp(11, entity.getTimestamp());
            if(optional.isPresent()){statement.setLong(12, optional.get().getId());}
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<QuoteDTO> findById(Integer id) throws IllegalArgumentException {
        if(id == null) throw new IllegalArgumentException();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE_BY_ID)){
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

    public Optional<QuoteDTO> findBySymbol(String symbol) throws IllegalArgumentException {
        if(symbol == null) throw new IllegalArgumentException();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE_BY_SYMBOL)){
            statement.setString(1, symbol);
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
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ALL)){
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
    public void deleteById(Integer id) throws IllegalArgumentException {
        if(id == null) throw new IllegalArgumentException();
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE)){
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE_ALL)){
            statement.executeUpdate();
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
        quoteDTO.setLatestTradingDay(rs.getDate("latest_trading_day"));
        quoteDTO.setPreviousClose(rs.getDouble("previous_close"));
        quoteDTO.setChange(rs.getDouble("change"));
        quoteDTO.setChangePercent(rs.getString("change_percent"));
        quoteDTO.setTimestamp(rs.getTimestamp("timestamp"));

        return quoteDTO;
    }
}
