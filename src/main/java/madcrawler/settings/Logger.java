package madcrawler.settings;

import static java.lang.String.format;

public class Logger {

    public static void log(Object message, Object... args) {
        System.out.println(format(String.valueOf(message), args));
    }
}
