package madcrawler.crawling;

import madcrawler.settings.CrawlerException;

public class TimeController {

    private long markedTime;

    public void mark() {
        markedTime = System.currentTimeMillis();
    }

    public void sleep() {
        checkForMarking();
        long elapsed = System.currentTimeMillis() - markedTime;
        if (elapsed < 1000) {
            tryToSleep(elapsed);
        }
    }

    private void tryToSleep(long elapsed) {
        try {
            Thread.sleep(1000 - elapsed);
        }
        catch (InterruptedException e) {
            throw new CrawlerException("Thread has been interrupted");
        }
    }

    private void checkForMarking() {
        if (markedTime == 0)
            throw new CrawlerException("Unmarked time in time controller");
    }
}
