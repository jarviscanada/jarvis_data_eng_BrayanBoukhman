package ca.jrvs.apps.jdbc.service;

import ca.jrvs.apps.jdbc.dao.PositionDAO;
import ca.jrvs.apps.jdbc.dto.Position;
import ca.jrvs.apps.jdbc.dto.Quote;

import java.util.List;

public class PositionService {

    private final PositionDAO dao;

    public PositionService(PositionDAO positionDAO) {
        this.dao = positionDAO;
    }

    public Integer buy(Quote quote, int numberOfShares) {
        if (quote.getVolume() >= numberOfShares && numberOfShares > 0) {
            Position position = new Position(quote.getSymbol(), numberOfShares, quote.getPrice());
            return dao.save(position);
        }
        return null;
    }

    public void sell(int id) {
        dao.deleteById(id);
    }

    public Iterable<Position> positionsBySymbol(String symbol) {
        return dao.findByAllSymbol(symbol);
    }

}
