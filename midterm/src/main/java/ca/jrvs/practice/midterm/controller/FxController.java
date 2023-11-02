package ca.jrvs.practice.midterm.controller;

import ca.jrvs.practice.midterm.helper.RateHttpHelper;
import ca.jrvs.practice.midterm.service.CurrencyService;
import ca.jrvs.practice.midterm.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FxController {

    private RateService rateService;
    private CurrencyService currService;
    private static final Logger logger = LoggerFactory.getLogger(FxController.class);


    public FxController(RateService service, CurrencyService currService) {
        logger.info("fxController has been created");
        this.rateService = service;
        this.currService = currService;
    }

    public void processExchange(String fromCode, String toCode, String date, String amount) throws ParseException, IOException {
        logger.info("processExchange was called with the values (" + fromCode + ","+ toCode + ","+ date + ","+ amount + ")");
        if(currService.isValidCode(fromCode)) {
            logger.error("invalid fromCode: " + fromCode);
            throw new RuntimeException("Invalid fromCode.");
        }
        if(currService.isValidCode(toCode)) {
            logger.error("invalid toCode: " + toCode);
            throw new RuntimeException("Invalid toCode.");
        }
        if(isValidDate(date)) {
            logger.error("invalid Date: " + date);
            throw new RuntimeException("Invalid date.");
        }
        if(isDouble(amount)) {
            logger.error("invalid Date: " + date);
            throw new RuntimeException("Invalid date.");
        }
        System.out.println(rateService.calculateExchange(fromCode, toCode, java.sql.Date.valueOf(date), Double.parseDouble(amount)));
    }

    public static boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            return dateStr.equals(sdf.format(date));
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isDouble(String input) {
        try {
            double value = Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
