package madcrawler.url;

public class UrlChecker {

    public boolean hasProtocol(String url) {
        return url.startsWith("http://");
    }
}