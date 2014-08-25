package madcrawler.settings;

import com.google.common.base.Stopwatch;

import static madcrawler.settings.Logger.log;

public class ExecutionTime {

    private static Stopwatch stopwatch;

    private ExecutionTime() {
        throw new CrawlerException("You should not instantiate this class");
    }

    public static void markStartPoint() {
        stopwatch = Stopwatch.createStarted();
        log("Mad Crawler app has been started");
    }

    public static void markEndPoint() {
        log("Total execution time is: %s", stopwatch);
        log("Mad Crawler app has been finished");
    }
}
