package samples.powermockito.testng.bugs;

import org.slf4j.Logger;

/**
 *
 */
public class Fuu {

    private static Logger LOGGER = LoggerFactory.getLogger(Fuu.class);

    public static boolean doStuff() {
        boolean traceEnabled = LOGGER.isTraceEnabled();
        System.out.println(traceEnabled);
        return traceEnabled;
    }
}
