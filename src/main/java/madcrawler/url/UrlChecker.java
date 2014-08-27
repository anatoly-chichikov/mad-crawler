package madcrawler.url;

import madcrawler.settings.CrawlerException;

import java.net.URL;

public class UrlChecker {

    private UrlChecker() {
        throw new CrawlerException("You should not instantiate this class");
    }

    public static boolean hasProtocol(String underTest) {
        return underTest.contains("://");
    }

    public static boolean isContainsFragment(String underTest) {
        return underTest.contains("#");
    }

    public static boolean isAbsValidProtocol(String underTest) {
        return underTest.startsWith("http://") ||
                underTest.startsWith("https://");
    }

    public static boolean isNotToPage(String underTest) {
        return underTest.startsWith("javascript:") ||
                underTest.startsWith("mailto:") ||
                underTest.startsWith("../");
    }

    public static boolean isWithSlash(String underTest) {
        return underTest.startsWith("/");
    }

    public static boolean isInternal(URL base, String underTest) {
        return underTest.matches("(http|https)://(.*\\.|)" + base.getHost() + "(/.*|)");
    }
}