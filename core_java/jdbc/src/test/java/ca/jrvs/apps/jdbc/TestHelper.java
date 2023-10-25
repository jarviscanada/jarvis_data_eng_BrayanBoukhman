package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.dto.PositionDTO;
import ca.jrvs.apps.jdbc.dto.QuoteDTO;

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
    public static QuoteDTO newQuoteDTO(){
        Random random = new Random();

        DecimalFormat df = new DecimalFormat("#.00");

        QuoteDTO quoteDTO = new QuoteDTO();

        quoteDTO.setSymbol(symbols[random.nextInt(53)]);
        quoteDTO.setOpen(Double.parseDouble(df.format(random.nextDouble() * 40.0 + 30.0)));
        quoteDTO.setHigh(Double.parseDouble(df.format(random.nextDouble() * 50.0 + 50.0)));
        quoteDTO.setLow(Double.parseDouble(df.format(random.nextDouble() * 40.0)));
        quoteDTO.setPrice(Double.parseDouble(df.format(random.nextDouble() * 70.0 + 10.0)));
        quoteDTO.setVolume(random.nextInt(50));
        quoteDTO.setLatestTradingDay(new Date(System.currentTimeMillis()));
        quoteDTO.setPreviousClose(Double.parseDouble(df.format(random.nextDouble() * 40.0 + 30.0)));
        quoteDTO.setChange(Double.parseDouble(df.format((quoteDTO.getPreviousClose() - quoteDTO.getOpen()) / quoteDTO.getPreviousClose())));
        quoteDTO.setChangePercent(df.format(quoteDTO.getChange() * 100) + "%");
        quoteDTO.setTimestamp(Timestamp.from(Instant.now()));

        return quoteDTO;
    }
    public static QuoteDTO newQuoteDTO(String symbol){
        QuoteDTO quoteDTO = newQuoteDTO();

        quoteDTO.setSymbol(symbol);

        return quoteDTO;
    }

    public static PositionDTO newPositionDTO(QuoteDTO quoteDTO){

        PositionDTO positionDTO = new PositionDTO();

        positionDTO.setSymbol(quoteDTO.getSymbol());
        positionDTO.setNumOfShares(quoteDTO.getVolume());
        positionDTO.setValuePaid(quoteDTO.getPrice());

        return positionDTO;
    }

    public static PositionDTO newPositionDTO(){
        return newPositionDTO(newQuoteDTO());
    }

    public static PositionDTO newPositionDTO(String symbol){
        return newPositionDTO(newQuoteDTO(symbol));
    }
}
