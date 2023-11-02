package ca.jrvs.practice.midterm.helper;

import ca.jrvs.practice.midterm.model.FxDailyDTO;
import ca.jrvs.practice.midterm.model.Rate;
import ca.jrvs.practice.midterm.repository.CurrencyRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class RateHttpHelper {

    private String apiKey;
    private String apiEndpoint;
    private OkHttpClient client;
    private static final Logger logger = LoggerFactory.getLogger(RateHttpHelper.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public RateHttpHelper(String apiKey, String apiEndpoint, OkHttpClient client) {
        logger.info("fxController has been created");
        this.apiKey = apiKey;
        this.apiEndpoint = apiEndpoint;
        this.client = client;
    }

    public Optional<Rate> fetchRate(String fromCode, String toCode, String date) throws IOException {
        logger.info("fetchRate has been called with (" + fromCode + ","+ toCode + ","+ date + ")");
        Request request = new Request.Builder()
                .url("https://alpha-vantage.p.rapidapi.com/query?to_currency=" + toCode + "&function=CURRENCY_EXCHANGE_RATE&from_currency=" + fromCode)
                .get()
                .addHeader("X-RapidAPI-Key", "bb2c8e4681mshff4fdc7648ea8fcp1b131ajsn7b26c7aefd26")
                .addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .build();

        try {
            Response response = client.newCall(request).execute();
            FxDailyDTO fxDailyDTO = objectMapper.readValue(response.body().string(), FxDailyDTO.class);
            return Optional.ofNullable(fxDailyDTO.getFxData().get(date));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
