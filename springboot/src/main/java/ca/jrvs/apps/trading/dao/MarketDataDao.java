package ca.jrvs.apps.trading.dao;

import java.util.List;
import java.util.Optional;

import ca.jrvs.apps.trading.model.IexQuote;
import org.apache.http.client.HttpClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketDataDao extends JpaRepository<IexQuote, String> {
    Optional<IexQuote> findById(String ticker);
    List<IexQuote> findAllById(Iterable<String> tickers);
    Optional<String> executeHttpGet(String url);
    HttpClient getHttpClient();
    boolean existsById(String s);
    List<IexQuote> findAll();
    long count();
    void deleteById(String s);
    void delete(IexQuote entity);
    void deleteAll(Iterable<? extends IexQuote> entities);
    void deleteAll();
    IexQuote save(IexQuote entity);
    List<IexQuote> saveAll(List<IexQuote> entities);
}