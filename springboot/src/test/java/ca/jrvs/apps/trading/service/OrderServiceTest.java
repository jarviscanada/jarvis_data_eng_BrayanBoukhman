package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.TestHelper;
import ca.jrvs.apps.trading.dao.TraderJpaRepository;
import ca.jrvs.apps.trading.model.MarketOrder;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.model.TraderAccountView;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TestConfig.class})
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    TraderAccountService traderAccountService;

    @Autowired
    TraderJpaRepository traderJpaRepository;
    @Before
    public void setUp() {
        if(!traderJpaRepository.existsById(1)) {
            Trader trader = TestHelper.createTestTrader();
            TraderAccountView traderAccountView = traderAccountService.createTraderAndAccount(trader);
            traderAccountView.getTrader();
        }
    }

    @Test
    void executeMarketOrderTest() {
        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setTicker("aapl");
        marketOrder.setSize(13);
        marketOrder.setOption("buy");
        marketOrder.setTraderId(1);

        SecurityOrder savedOrder = orderService.executeMarketOrder(marketOrder);

        assertNotNull(savedOrder);
        assertEquals(savedOrder.getQuote().getTicker(), marketOrder.getTicker());
    }
}
