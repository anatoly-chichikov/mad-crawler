package madcrawler.url;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UrlCheckerTest {

    @Test
    public void testHasProtocol() {
        UrlChecker checker = new UrlChecker();
        String url = "http://test.me";

        assertTrue(checker.hasProtocol(url));
    }

    @Test
    public void testWithoutProtocol() {
        UrlChecker checker = new UrlChecker();
        String url = "test.me";

        assertFalse(checker.hasProtocol(url));
    }
}