package ca.jrvs.apps.jdbc.helper;
import ca.jrvs.apps.jdbc.dto.QuoteApiResponseDTO;
import ca.jrvs.apps.jdbc.dto.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public class ApiManager {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static OkHttpClient client = new OkHttpClient();

    public static Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
        Request request = new Request.Builder()
                .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol +"&datatype=json")
                .get()
                .addHeader("X-RapidAPI-Key", PropertyManager.getApiKey())
                .addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .build();

        //Response response = client.newCall(request).execute();
        try {
            Response response = client.newCall(request).execute();
            if(response.body() != null) {
                QuoteApiResponseDTO quoteApiResponseDTO = objectMapper.readValue(response.body().string(), QuoteApiResponseDTO.class);
                return quoteApiResponseDTO.getQuote();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}