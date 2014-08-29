package madcrawler.io;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import madcrawler.settings.CrawlerException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

public class FileReader {

    public List<String> readUrlsAsLines(String path) throws IOException {
        return Files.readLines(ensureExistence(path), Charsets.UTF_8);
    }

    private File ensureExistence(String path) {
        File source = new File(path);
        if (!source.isFile())
            throw new CrawlerException(
                    format("Unreachable file: %s.", source.getAbsolutePath()));
        return source;
    }
}
