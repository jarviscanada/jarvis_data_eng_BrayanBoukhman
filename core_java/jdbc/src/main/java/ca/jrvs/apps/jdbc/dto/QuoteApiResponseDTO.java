package ca.jrvs.apps.jdbc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuoteApiResponseDTO {
    @JsonProperty("Global Quote")
    private Quote quote;
}
