package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LilithSample {
    public static void main(String args[]){
        Logger logger = LoggerFactory.getLogger(LilithSample.class);

        logger.info("Start");
        logger.debug("こんにちは");
        logger.info("End");
    }
}
