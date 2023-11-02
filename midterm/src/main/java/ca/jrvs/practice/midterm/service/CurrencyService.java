package ca.jrvs.practice.midterm.service;

import ca.jrvs.practice.midterm.repository.CurrencyRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrencyService {

    private CurrencyRepo repo;

    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    public CurrencyService(CurrencyRepo repo) {
        this.repo = repo;
    }

    public boolean isValidCode(String code) {
        return repo.getCurrency(code).isPresent();
    }

}
