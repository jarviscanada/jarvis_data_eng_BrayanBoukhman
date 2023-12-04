package ca.jrvs.apps.trading.helper;

import ca.jrvs.apps.trading.configuration.AppConfig;
import ca.jrvs.apps.trading.model.IexQuote;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MarketDataHttpHelper {
    final Logger logger = LoggerFactory.getLogger(MarketDataHttpHelper.class);
    private final String IEX_TOKEN;
    private final String STABLE_API_BASE = "https://cloud.iexapis.com/stable/stock/";
    private final String BATCH_API_BASE = "https://cloud.iexapis.com/v1/stock/market/";

    public MarketDataHttpHelper() {
        this.IEX_TOKEN = AppConfig.getProperty("IEX_TOKEN");
    }

    public Optional<IexQuote> getIexQuote(String ticker) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(STABLE_API_BASE + ticker + "/quote?token=" + IEX_TOKEN))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return Optional.ofNullable(mapper.readValue(response.body(), IexQuote.class));
        } catch (IOException | InterruptedException e) {
            logger.error("Error fetching quote: " + e);
        }

        return Optional.empty();
    }

    public Optional<List<IexQuote>> getBatchQuote(Iterable<String> tickers) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String tickersString = StreamSupport.stream(tickers.spliterator(), false)
                    .collect(Collectors.joining(","));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BATCH_API_BASE + "batch?symbols=" + tickersString + "&types=quote&token=" + IEX_TOKEN))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            JavaType mapType = mapper.getTypeFactory().constructMapType(Map.class, String.class, IexQuote.class);
            Map<String, IexQuote> quoteMap = mapper.readValue(response.body(), mapType);

            List<IexQuote> quoteList = new ArrayList<>(quoteMap.values());
            return Optional.of(quoteList);
        } catch (IOException | InterruptedException e) {
            logger.error("Error fetching quote batch: ", e);
        }

        return Optional.empty();
    }
}
