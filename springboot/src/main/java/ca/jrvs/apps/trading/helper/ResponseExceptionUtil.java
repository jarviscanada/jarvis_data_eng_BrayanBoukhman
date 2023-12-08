package ca.jrvs.apps.trading.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResponseExceptionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ResponseExceptionUtil.class);

    public static ResponseStatusException getResponseStatusException(Exception ex) {
        if(ex instanceof IllegalArgumentException) {
            logger.error("Invalid argument(s): " + ex);
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        else {
            logger.error("Internal Server Error: " + ex);
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }
}