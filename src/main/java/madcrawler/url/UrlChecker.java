package madcrawler.url;

import madcrawler.settings.CrawlerException;

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

    public static boolean isWithSlash(String path) {
        return path.startsWith("/");
    }
}