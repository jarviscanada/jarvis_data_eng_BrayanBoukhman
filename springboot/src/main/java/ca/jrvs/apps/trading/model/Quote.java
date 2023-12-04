package ca.jrvs.apps.trading.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
public class Quote {

    @Id
    @Column(name = "ticker", columnDefinition = "VARCHAR(255)")
    private String ticker;

    @Column(name = "last_price")
    private double lastPrice;

    @Column(name = "bid_price")
    private double bidPrice;

    @Column(name = "bid_size")
    private int bidSize;

    @Column(name = "ask_price")
    private double askPrice;

    @Column(name = "ask_size")
    private int askSize;
}
