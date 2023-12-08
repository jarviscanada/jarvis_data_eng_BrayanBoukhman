package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.Trader;
import java.sql.Date;

public class TestHelper {


    public static Trader createTestTrader() {
        Trader trader = new Trader();
        trader.setFirstName("tim");
        trader.setLastName("tom");
        trader.setDob(new Date(1998,8,1));
        trader.setCountry("wow");
        trader.setEmail("BrayanBoukhman.ca");
        return trader;
    }
}
