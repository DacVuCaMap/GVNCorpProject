package com.GVNCop.app.Entity;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

public class LogClass {
    private final Logger logger = (Logger) LoggerFactory.getLogger(LogClass.class);

    public void myMethod() {
        logger.info("This is an info message.");
        logger.error("This is an error message.");
    }
}