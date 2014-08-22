package madcrawler.io;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.inject.Inject;
import madcrawler.settings.CrawlerException;
import madcrawler.url.UrlChecker;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;

public class UrlsReader {

    UrlChecker checker;

    public Set<URL> getUrlsFromFile(File source) {
        Set<URL> result;
        try {
            result = tryToConvertLinesToUrls(Files.readLines(source, Charsets.UTF_8));
        }
        catch (IOException e) {
            throw new CrawlerException(
                format("Can't read file: %s",
                source.getAbsolutePath()));
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
    public void setChecker(UrlChecker checker) {
        this.checker = checker;
    }
}
