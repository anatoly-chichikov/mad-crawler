package madcrawler.url;

import com.google.common.base.Objects;

import java.net.URL;
import java.util.Set;

import static com.google.common.base.Objects.equal;
import static java.lang.String.format;

public class PageUrls {

    private URL page;
    private Set<String> links;

    public PageUrls(URL page, Set<String> links) {
        this.page = page;
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageUrls)) return false;
        PageUrls that = (PageUrls) o;

        return equal(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(page);
    }

    @Override
    public String toString() {
        return format(
                "Parsed URL: %s found links: %s pcs\n",
                page.toString(),
                links.size());
    }

    public URL getPage() {
        return page;
    }

    public Set<String> getLinks() {
        return links;
    }


}
