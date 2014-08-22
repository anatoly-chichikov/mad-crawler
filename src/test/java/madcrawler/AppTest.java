package madcrawler;

import madcrawler.settings.CrawlerException;
import org.junit.Test;

public class AppTest {

    @Test(expected = CrawlerException.class)
    public void testStartWithoutArgs() {
        App.main(new String[]{});
    }
}