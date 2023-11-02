package ca.jrvs.practice.midterm;

import ca.jrvs.practice.midterm.controller.FxController;
import ca.jrvs.practice.midterm.helper.RateHttpHelper;
import ca.jrvs.practice.midterm.repository.CurrencyRepo;
import ca.jrvs.practice.midterm.repository.DailyRateRepo;
import ca.jrvs.practice.midterm.service.CurrencyService;
import ca.jrvs.practice.midterm.service.RateService;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        String url = "jdbc:postgresql://localhost:5432/postgres";
        try (Connection c = DriverManager.getConnection(url, "postgres", "password")) {
            DailyRateRepo rateRepo = new DailyRateRepo(c);
            CurrencyRepo currRepo = new CurrencyRepo(c);
            RateHttpHelper http = new RateHttpHelper("bb2c8e4681mshff4fdc7648ea8fcp1b131ajsn7b26c7aefd26", "FX_DAILY", client);
            RateService rateService = new RateService(rateRepo, http);
            CurrencyService currService = new CurrencyService(currRepo);
            FxController controller = new FxController(rateService, currService);
            controller.processExchange(args[0], args[1], args[2], args[3]);
        } catch (SQLException e) {
            logger.error("Could not open connection to database - " + e);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
