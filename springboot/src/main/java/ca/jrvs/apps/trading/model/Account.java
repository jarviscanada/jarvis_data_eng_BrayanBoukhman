package ca.jrvs.apps.trading.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Account {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "trader_id", referencedColumnName = "id")
    private Trader trader;

    @Column(name = "amount")
    private double amount;
}
