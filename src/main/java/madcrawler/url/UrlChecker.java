package madcrawler.url;

import madcrawler.settings.CrawlerException;

import java.net.URI;
import java.net.URL;

import static com.google.common.base.Objects.equal;

public class UrlChecker {

    private UrlChecker() {
        throw new CrawlerException("You should not instantiate this class");
    }

    public static boolean hasProtocol(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    public static boolean isContainsFragment(String underTest) {
        return underTest.contains("#");
    }

    public static boolean isAbsoluteExternal(URI underTest, URL base) {
        return underTest.isAbsolute() &&
                !equal(underTest.getHost(), base.getHost()) &&
                !underTest.getHost().endsWith(base.getHost());
    }

    public static boolean isAbsoluteInternal(URI underTest, URL base) {
        return underTest.isAbsolute() &&
                underTest.getHost().endsWith(base.getHost());
    }

    public static boolean isValidProtocol(URI underTest) {
        return equal(underTest.getScheme(), "http") ||
                equal(underTest.getScheme(), "https");
    }

    public static boolean isWithSlash(URI path) {
        return path.getPath().startsWith("/");
    }
}