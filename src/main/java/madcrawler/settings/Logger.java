package madcrawler.settings;

import static java.lang.String.format;

public class Logger {

    private Logger() {
        throw new CrawlerException("You should not instantiate this class");
    }

    public static void log(Object message, Object... args) {
        if (args.length == 0)
            System.out.println(String.valueOf(message));
        else
            System.out.println(format(String.valueOf(message), args));
    }
}
