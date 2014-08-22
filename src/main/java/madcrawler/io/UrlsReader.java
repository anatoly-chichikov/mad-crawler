package madcrawler.io;

import com.google.inject.Inject;
import madcrawler.settings.CrawlerException;
import madcrawler.url.UrlChecker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;

public class UrlsReader {

    UrlChecker checker;
    FileReader fReader;

    public Set<URL> getUrlsFromFile(String source) {
        Set<URL> result;
        try {
            result = tryToConvertLinesToUrls(fReader.readUrlsAsLines(source));
        }
        catch (IOException e) {
            throw new CrawlerException(
                format("Can't read file: %s", source));
        }
        return result;
    }

    private Set<URL> tryToConvertLinesToUrls(List<String> strings) {
        Set<URL> result = newHashSet();
        for (String candidate : strings) {
            tryToAddUrl(result, candidate);
        }
        return result;
    }

    private void tryToAddUrl(Set<URL> result, String candidate) {
        try {
            if (checker.hasProtocol(candidate)) result.add(new URL(candidate));
            else result.add(new URL(format("http://%s", candidate)));
        }
        catch (MalformedURLException e) {
            throw new CrawlerException(format("Bad URL found: %s", candidate));
        }
    }

    @Inject
    public void setUrlChecker(UrlChecker checker) {
        this.checker = checker;
    }

    @Inject
    public void setFileReader(FileReader fReader) {
        this.fReader = fReader;
    }
}
