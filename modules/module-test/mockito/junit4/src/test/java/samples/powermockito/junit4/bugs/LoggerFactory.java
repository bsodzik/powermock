package samples.powermockito.junit4.bugs;

import org.slf4j.Logger;

/**
 *
 */
public class LoggerFactory {

    public static Logger getLogger(Class<Fuu> fuuClass) {
        return  org.slf4j.LoggerFactory.getLogger(fuuClass);
    }

}
