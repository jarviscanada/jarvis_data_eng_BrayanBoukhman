package ca.jrvs.apps.trading.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Position {
    @Id
    private int accountId;
    private String ticker;
    private long position;

    public Position(Long position, int accountId, String ticker) {
        this.position = position;
        this.accountId = accountId;
        this.ticker = ticker;
    }

    public Position() {

    }
}
