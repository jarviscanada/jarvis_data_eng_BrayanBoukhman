package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.TestHelper;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.model.TraderAccountView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TestConfig.class})
class TraderAccountServiceTest {
    @Autowired
    private TraderAccountService traderAccountService;

    @Test
    void createTraderAndAccountTest() {
        Trader trader = TestHelper.createTestTrader();

        TraderAccountView savedTraderAccountView = traderAccountService.createTraderAndAccount(trader);

        assertEquals(trader, savedTraderAccountView.getTrader());
        assertEquals(savedTraderAccountView.getTrader().getId(), savedTraderAccountView.getAccount().getId());
    }
}