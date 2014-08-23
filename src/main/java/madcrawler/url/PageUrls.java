package madcrawler.url;

import com.google.common.base.Objects;

import java.net.URL;
import java.util.Set;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Objects.equal;
import static java.lang.String.format;

public class PageUrls {

    private URL page;
    private Set<URL> externalLinks;
    private Set<URL> internalLinks;

    public PageUrls(URL page, Set<URL> externalLinks, Set<URL> internalLinks) {
        this.page = page;
        this.externalLinks = externalLinks;
        this.internalLinks = internalLinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageUrls)) return false;
        PageUrls that = (PageUrls) o;

        return equal(page, that.page)
            && equal(externalLinks, that.externalLinks)
            && equal(internalLinks, that.internalLinks);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(externalLinks, internalLinks);
    }

    @Override
    public String toString() {
        return format(
            "Parsed URL:\n\t%s\n" +
            "External links:\n\t%s\n" +
            "Internal links:\n\t%s\n",
            page.toString(),
            on("\n\t").join(externalLinks),
            on("\n\t").join(internalLinks));
    }

    public URL getPage() {
        return page;
    }

    public Set<URL> getExternalLinks() {
        return externalLinks;
    }

    public Set<URL> getInternalLinks() {
        return internalLinks;
    }
}