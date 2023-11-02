package ca.jrvs.practice.midterm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FxDailyDTO {
    @JsonProperty("Time Series FX (Daily)")
    private Map<String, Rate> fxData;
}
