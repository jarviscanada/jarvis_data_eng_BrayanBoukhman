package ca.jrvs.apps.jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PositionDTO {
    private int id;
    private String symbol;
    private int numOfShares;
    private double valuePaid;
    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", numOfShares=" + numOfShares +
                ", valuePaid=" + valuePaid +
                '}';
    }
}
