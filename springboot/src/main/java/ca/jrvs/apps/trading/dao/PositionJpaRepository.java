package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionJpaRepository extends JpaRepository<Position, Integer> {
    @Query("SELECT NEW ca.jrvs.apps.trading.model.Position(SUM(s.size), s.account.id, s.quote.ticker) " +
            "FROM SecurityOrder s " +
            "WHERE s.status = 'FILLED' AND s.account.id = :accountId " +
            "GROUP BY s.account.id, s.quote.ticker")
    public List<Position> getPositionsByAccountId(@Param("accountId") int accountId);

    @Query("SELECT NEW ca.jrvs.apps.trading.model.Position(SUM(s.size), s.account.id, s.quote.ticker) " +
            "FROM SecurityOrder s " +
            "WHERE s.status = 'FILLED' AND s.account.id = :accountId AND s.quote.ticker = :ticker")
    Position findByAccountIdAndTicker(@Param("accountId") int accountId, @Param("ticker") String ticker);
}
