package ca.jrvs.apps.trading.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TraderAccountView {
    private Trader trader;
    private Account account;
}
