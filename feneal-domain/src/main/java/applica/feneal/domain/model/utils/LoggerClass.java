package applica.feneal.domain.model.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/13/15
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoggerClass {

    private static final Logger logger = LogManager.getLogger();



    public static void info(String message){
        logger.info(message);
    }

    public static void error(String message){
        logger.error(message);
    }
}
