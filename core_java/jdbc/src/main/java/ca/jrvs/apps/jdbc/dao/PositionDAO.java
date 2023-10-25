package ca.jrvs.apps.jdbc.dao;

import ca.jrvs.apps.jdbc.dto.PositionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PositionDAO extends CrudDAO<PositionDTO, Integer> {
    private static final String SAVE = "INSERT INTO stock_quote_app.position " +
            "(symbol, number_of_shares, value_paid) " +
            "VALUES (?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM stock_quote_app.position WHERE id = ?";
    private static final String FIND_ALL_BY_SYMBOL = "SELECT * FROM stock_quote_app.position WHERE symbol = ?";
    private static final String FIND_ALL = "SELECT * FROM stock_quote_app.position";
    private static final String DELETE = "DELETE FROM stock_quote_app.position WHERE id = ?";
    private static final String DELETE_ALL = "TRUNCATE TABLE stock_quote_app.position CASCADE";
    public PositionDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Integer save(PositionDTO entity) throws IllegalArgumentException {
        if(entity == null) throw new IllegalArgumentException();

        try (PreparedStatement statement = this.connection.prepareStatement(SAVE)) {
            statement.setString(1, entity.getSymbol());
            statement.setInt(2, entity.getNumOfShares());
            statement.setDouble(3, entity.getValuePaid());
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
    public Optional<PositionDTO> findById(Integer id) throws IllegalArgumentException {
        if(id == null) throw new IllegalArgumentException();
        try(PreparedStatement statement = this.connection.prepareStatement(FIND_BY_ID)){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                PositionDTO position = new PositionDTO();

                position.setId(rs.getInt(1));
                position.setSymbol(rs.getString(2));
                position.setNumOfShares(rs.getInt(3));
                position.setValuePaid(rs.getDouble(4));

                return Optional.of(position);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Iterable<PositionDTO> findByAllSymbol(String symbol) throws IllegalArgumentException {
        if(symbol == null) throw new IllegalArgumentException();

        List<PositionDTO> positions = new ArrayList<>();

        try (PreparedStatement statement = this.connection.prepareStatement(FIND_ALL_BY_SYMBOL)) {
            statement.setString(1, symbol);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                PositionDTO position = new PositionDTO();

                position.setId(rs.getInt(1));
                position.setSymbol(rs.getString(2));
                position.setNumOfShares(rs.getInt(3));
                position.setValuePaid(rs.getDouble(4));
                positions.add(position);
            }
            return positions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<PositionDTO> findAll() {
        List<PositionDTO> positions = new ArrayList<>();
        try (PreparedStatement statement = this.connection.prepareStatement(FIND_ALL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PositionDTO position = new PositionDTO();
                position.setId(rs.getInt(1));
                position.setSymbol(rs.getString(2));
                position.setNumOfShares(rs.getInt(3));
                position.setValuePaid(rs.getDouble(4));
                positions.add(position);
            }
            return positions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
}
