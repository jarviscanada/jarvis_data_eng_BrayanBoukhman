package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.dto.Position;
import ca.jrvs.apps.jdbc.dto.Quote;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.Random;

public class TestHelper {
    private static final String[] symbols = new String[] {"MSFT", "AAPL", "GOOGL",
            "AMZN", "TSLA", "FB", "NFLX", "JPM", "GOOG", "INTC",
            "CSCO", "ORCL", "IBM", "CRM", "AMD", "NVDA", "ADBE",
            "QCOM", "AMAT", "HPQ", "HP", "CTSH", "LNVGY", "ZM",
            "CRM", "SQ", "V", "PYPL", "MA", "VZ", "T", "S", "TMUS",
            "AXP", "BA", "MCD", "KO", "PEP", "DIS", "NFLX", "NKE",
            "GE", "GM", "F", "UBER", "LYFT", "BAC", "GS", "WFC",
            "JNJ", "PFE", "MRK", "ABBV"};
    public static Quote newQuoteDTO(){
        Random random = new Random();

        DecimalFormat df = new DecimalFormat("#.00");

        Quote quote = new Quote();

        quote.setSymbol(symbols[random.nextInt(53)]);
        quote.setOpen(Double.parseDouble(df.format(random.nextDouble() * 40.0 + 30.0)));
        quote.setHigh(Double.parseDouble(df.format(random.nextDouble() * 50.0 + 50.0)));
        quote.setLow(Double.parseDouble(df.format(random.nextDouble() * 40.0)));
        quote.setPrice(Double.parseDouble(df.format(random.nextDouble() * 70.0 + 10.0)));
        quote.setVolume(random.nextInt(50));
        quote.setLatestTradingDay(new Date(System.currentTimeMillis()));
        quote.setPreviousClose(Double.parseDouble(df.format(random.nextDouble() * 40.0 + 30.0)));
        quote.setChange(Double.parseDouble(df.format((quote.getPreviousClose() - quote.getOpen()) / quote.getPreviousClose())));
        quote.setChangePercent(df.format(quote.getChange() * 100) + "%");
        quote.setTimestamp(Timestamp.from(Instant.now()));

        return quote;
    }
    public static Quote newQuoteDTO(String symbol){
        Quote quote = newQuoteDTO();

        quote.setSymbol(symbol);

        return quote;
    }

    public static Position newPositionDTO(Quote quote){

        Position position = new Position();

        position.setSymbol(quote.getSymbol());
        position.setNumOfShares(quote.getVolume());
        position.setValuePaid(quote.getPrice());

        return position;
    }

    public static Position newPositionDTO(){
        return newPositionDTO(newQuoteDTO());
    }

    public static Position newPositionDTO(String symbol){
        return newPositionDTO(newQuoteDTO(symbol));
    }
}
