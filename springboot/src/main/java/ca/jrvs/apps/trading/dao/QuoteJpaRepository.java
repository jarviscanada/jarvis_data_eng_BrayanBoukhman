package ca.jrvs.apps.trading.dao;

import java.util.List;
import java.util.Optional;

import ca.jrvs.apps.trading.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteJpaRepository extends JpaRepository<Quote, String> {
}