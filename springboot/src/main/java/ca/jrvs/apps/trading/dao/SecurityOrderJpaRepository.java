package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.model.SecurityOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SecurityOrderJpaRepository extends JpaRepository<SecurityOrder, Integer> {
    @Transactional
    List<SecurityOrder> findAllByAccountIdAndStatus(int accountId, String status);

    @Transactional
    void deleteAllByAccountId(int accountId);
}