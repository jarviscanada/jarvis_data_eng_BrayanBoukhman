package ca.jrvs.practice.midterm.service;

import ca.jrvs.practice.midterm.Main;
import ca.jrvs.practice.midterm.helper.RateHttpHelper;
import ca.jrvs.practice.midterm.model.Rate;
import ca.jrvs.practice.midterm.repository.DailyRateRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

public class RateService {

    private DailyRateRepo repo;
    private RateHttpHelper http;

    private static final Logger logger = LoggerFactory.getLogger(RateService.class);

    public RateService(DailyRateRepo repo, RateHttpHelper http) {
        this.repo = repo;
        this.http = http;
    }

    public double calculateExchange(String fromSymbol, String toSymbol, Date date, double amount) throws IOException {
        logger.error("calculateExchange was called with the values (" + fromSymbol + ","+ toSymbol + ","+ date + ","+ amount + ")");
        Optional<Rate> optionalRepoCall = repo.getRate(date, fromSymbol, toSymbol);

        if(optionalRepoCall.isPresent()) {
            return optionalRepoCall.get().getClose() * amount;
        } else {
            Optional<Rate> optionalApiCall = http.fetchRate(fromSymbol, toSymbol, date.toString());
            if(optionalApiCall.isPresent()) {
                return optionalApiCall.get().getClose() * amount;
            } else {
                logger.error("not in Database and api call failed for the values (" + fromSymbol + ","+ toSymbol + ","+ date + ","+ amount + ")");
                System.out.println("exchange failed");
                throw new RuntimeException("The api call went through but did not return the value needed.");
            }
        }
    }

}
