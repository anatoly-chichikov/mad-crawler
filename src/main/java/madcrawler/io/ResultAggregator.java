package madcrawler.io;

import com.google.common.base.Throwables;
import madcrawler.settings.CrawlerException;
import madcrawler.url.PageUrls;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static com.google.common.collect.Sets.newTreeSet;
import static com.google.common.io.Files.touch;
import static java.lang.String.format;
import static madcrawler.settings.Logger.log;

public class ResultAggregator {

    private Set<String> links = newTreeSet();

    public void add(PageUrls urls) {
        if (urls != null) {
            saveNonEmptyResult(urls);
        }
    }

    public void writeResultFile() {
        try {
            tryToWrite();
        }
        catch (Exception e) {
            throw new CrawlerException(
                    format("Can't write result file: %s\n%s", e.getMessage(),
                            Throwables.getStackTraceAsString(e)));
        }
    }

    private void saveNonEmptyResult(PageUrls urls) {
        for (String url : urls.getLinks())
            links.add(url);
    }

    private void tryToWrite() throws IOException {
        File result = createNewFile();
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(result));

        writeLinks(writer);
        writer.close();

        log("Writing %s urls...", links.size());
        log("Result file %s has been written\n", result.getName());
    }

    private void writeLinks(BufferedWriter writer) throws IOException {
        for (String link : links)
            writer.write(link + '\n');
    }

    private File createNewFile() throws IOException {
        File newFile = new File(format("result-%s.txt", stampDateAndTime()));
        touch(newFile);
        return newFile;
    }

    private String stampDateAndTime() {
        return new SimpleDateFormat("dd.MM.yyyy-(HH|mm|ss)").
                format(new Date());
    }

}
