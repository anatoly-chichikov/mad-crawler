package madcrawler.crawling;

import com.google.inject.Inject;
import madcrawler.url.PageUrls;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static madcrawler.settings.Logger.log;

public class PageProcessor {

    @Inject private PageDownloader downloader;

    public PageUrls process(URL target) {
        try {
            return new PageUrls(
                    target,
                    tryToGetAnchors(target));
        }
        catch (Exception e) {
            log("Can't process page: %s\nCause: %s\n", target, e.getMessage());
            return null;
        }
    }

    private Set<String> tryToGetAnchors(URL target) throws Exception {
        Set<String> result = newHashSet();
        for (Element anchor : getAnchors(target))
            result.add(anchor.attr("href"));
        return result;
    }

    private Elements getAnchors(URL target) throws IOException {
        return downloader.fetchPage(target).select("a");
    }

    public void setDownloader(PageDownloader downloader) {
        this.downloader = downloader;
    }
}
