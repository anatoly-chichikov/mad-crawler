package madcrawler.url;

import java.net.URI;
import java.net.URL;

import static com.google.common.base.Objects.equal;

public class UrlChecker {

    public boolean hasProtocol(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    public boolean isAbsoluteExternal(URI underTest, URL base) {
        return underTest.isAbsolute() &&
            !equal(underTest.getHost(), base.getHost());
    }

    public boolean isAbsoluteInternal(URI underTest, URL base) {
        return underTest.isAbsolute() &&
            equal(underTest.getHost(), base.getHost());
    }

    public boolean isValidProtocol(URI underTest) {
        return equal(underTest.getScheme(), "http") ||
            equal(underTest.getScheme(), "https");
    }

    public boolean isContainsFragment(URI underTest) {
        return underTest.getFragment() != null;
    }
}