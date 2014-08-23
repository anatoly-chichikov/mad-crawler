package madcrawler.settings;

import static java.lang.String.format;

public class Logger {

    public static void log(Object message, Object... args) {
        if (args.length == 0)
            System.out.println(String.valueOf(message));
        else
            System.out.println(format(String.valueOf(message), args));
    }
}
