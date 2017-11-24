package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculator {
    Logger logger;

    public Calculator() {
        logger = LoggerFactory.getLogger(Calculator.class);
    }

    int add(int a, int b) {
        logger.info("Start");

        int c = a + b;

        logger.info("End");
        return c;
    }
}