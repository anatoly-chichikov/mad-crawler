package madcrawler.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

public class PageDownloader {

    public Document fetchPage(URL url) throws IOException {
        return Jsoup.connect(url.toString())
                .timeout(1000).followRedirects(true).get();
    }
}
