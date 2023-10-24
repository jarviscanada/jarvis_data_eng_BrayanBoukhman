package ca.jrvs.apps.jdbc.helper;
import ca.jrvs.apps.jdbc.dto.QuoteApiResponseDTO;
import ca.jrvs.apps.jdbc.dto.QuoteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public class QuoteHttpHelper {

    private final ObjectMapper objectMapper;
    private final OkHttpClient client;
    private final String apiKey;

    public QuoteHttpHelper(String apiKey) {
        this.objectMapper = new ObjectMapper();
        this.client = new OkHttpClient();
        this.apiKey = apiKey;
    }

    /**
     * Fetch latest quote data from Alpha Vantage endpoint
     * @param symbol
     * @return Quote with latest data
     * @throws IllegalArgumentException - if no data was found for the given symbol
     */
    public QuoteDTO fetchQuoteInfo(String symbol) throws IllegalArgumentException {
        Request request = new Request.Builder()
                .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol +"&datatype=json")
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .build();

        //Response response = client.newCall(request).execute();
        try {
            Response response = client.newCall(request).execute();
            if(response.body() != null) {
                QuoteApiResponseDTO quoteApiResponseDTO = objectMapper.readValue(response.body().string(), QuoteApiResponseDTO.class);
                return quoteApiResponseDTO.getQuoteDTO();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}