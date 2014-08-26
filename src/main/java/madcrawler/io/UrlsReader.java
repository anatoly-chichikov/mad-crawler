package madcrawler.io;

import com.google.inject.Inject;
import madcrawler.settings.CrawlerException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static madcrawler.url.UrlChecker.hasProtocol;

public class UrlsReader {

    @Inject private FileReader fReader;

    public Set<URL> getUrlsFromFile(String source) {
        try {
            return tryToConvertLinesToUrls(fReader.readUrlsAsLines(source));
        }
        catch (IOException e) {
            throw new CrawlerException(
                    format("Can't read file: %s", source));
        }
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
            if (hasProtocol(candidate)) result.add(new URL(candidate));
            else result.add(new URL(format("http://%s", candidate)));
        }
        catch (MalformedURLException e) {
            throw new CrawlerException(format("Bad URL found: %s", candidate));
        }
    }

    public void setFileReader(FileReader fReader) {
        this.fReader = fReader;
    }
}
