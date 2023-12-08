package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.helper.MarketDataHttpHelper;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"ca.jrvs.apps"})
public class TestConfig implements EnvironmentAware {
    private Environment env;
    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

}