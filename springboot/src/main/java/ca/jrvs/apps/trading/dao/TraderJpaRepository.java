package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.model.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderJpaRepository extends JpaRepository<Trader, Integer> {
}