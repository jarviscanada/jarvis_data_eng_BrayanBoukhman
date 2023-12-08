package ca.jrvs.apps.trading.model;

import lombok.Data;

@Data
public class MarketOrder {
    private String ticker;

    private String option;
    private int size;

    private int traderId;
}
