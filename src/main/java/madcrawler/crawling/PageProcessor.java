package madcrawler.crawling;

import com.google.inject.Inject;
import madcrawler.url.PageUrls;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import static com.google.common.base.Splitter.on;
import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.net.UrlEscapers.urlFragmentEscaper;
import static madcrawler.settings.Logger.log;
import static madcrawler.url.UrlChecker.*;

public class PageProcessor {

    @Inject private PageDownloader downloader;

    public PageUrls process(URL target) {
        try {
            return getPageUrls(
                    target,
                    getUris(getAnchors(target)));
        }
        catch (Exception e) {
            log("Can't process page: %s\nCause: %s\n", target, e.getMessage());
            return null;
        }
    }

    private PageUrls getPageUrls(URL target, Set<URI> uris) throws Exception {
        return new PageUrls(
                target,
                handleExternalLinks(uris, target),
                handleInternalLinks(uris, target));
    }

    private Set<URL> handleInternalLinks(Set<URI> uris, URL target) throws Exception {
        Set<URL> result = newHashSet();
        for (URI anchor : uris) {
            if (isValidProtocol(anchor) &&
                    isAbsoluteInternal(anchor, target))
                result.add(anchor.toURL());
            if (!anchor.isAbsolute())
                result.add(ensureSlashes(target, anchor));
        }
        return result;
    }

    private Set<URL> handleExternalLinks(Set<URI> uris, URL target) throws Exception {
        Set<URL> result = newHashSet();
        for (URI anchor : uris)
            if (isValidProtocol(anchor) &&
                    isAbsoluteExternal(anchor, target))
                result.add(anchor.toURL());
        return result;
    }

    private URL ensureSlashes(URL host, URI path) throws Exception {
        if (isWithSlash(path))
            return new URL(
                    host.getProtocol(),
                    host.getHost(),
                    path.getPath());
        else return new URL(
                host.getProtocol(),
                host.getHost(),
                "/" + path.getPath());
    }

    private Set<URI> getUris(Elements anchors) {
        Set<URI> uris = newHashSet();
        for (Element anchor : anchors) {
            try {
                uris.add(new URI(prepare(anchor.attr("href"))));
            }
            catch (URISyntaxException e) {
                log("Unprocessed link: %s\n", prepare(anchor.attr("href")));
            }
        }
        return uris;
    }

    private String prepare(String href) {
        if (isContainsFragment(href))
            return urlFragmentEscaper().escape(
                    on('#').split(href).iterator().next());
        return urlFragmentEscaper().escape(href);
    }

    private Elements getAnchors(URL target) throws IOException {
        return downloader.fetchPage(target).select("a");
    }

    public void setDownloader(PageDownloader downloader) {
        this.downloader = downloader;
    }
}
