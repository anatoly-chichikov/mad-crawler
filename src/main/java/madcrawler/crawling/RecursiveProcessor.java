package madcrawler.crawling;

import com.google.inject.Inject;
import madcrawler.settings.CrawlerException;
import madcrawler.url.PageUrls;
import madcrawler.url.UrlFixer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;
import static madcrawler.settings.Logger.log;

public class RecursiveProcessor implements Iterator<PageUrls>, Iterable<PageUrls> {

    @Inject private PageProcessor processor;
    @Inject private UrlFixer fixer;
    private int alreadyProcessed;
    private boolean isUsed;

    private URL base;
    private Queue<PageUrls> results = newLinkedList();
    private Queue<String> toProcess = newLinkedList();
    private Set<String> uniqueProcessed = newHashSet();

    public RecursiveProcessor(URL base) {
        this.base = base;
    }

    @Override
    public boolean hasNext() {
        return !isUsed || !toProcess.isEmpty();
    }

    @Override
    public PageUrls next() {
        try {
            tryToProcessNextUrl();
        }
        catch (MalformedURLException e) {
            log("Error during getting next link: ", e.getMessage());
        }
        return results.poll();
    }

    @Override
    public void remove() {
        throw new CrawlerException("Unsupported operation");
    }

    @Override
    public Iterator<PageUrls> iterator() {
        return this;
    }

    private void tryToProcessNextUrl() throws MalformedURLException {
        if (!isUsed) processFirst();
        else processNext();
    }

    private void processFirst() {
        isUsed = true;
        uniqueProcessed.add(base.toString());
        PageUrls result = processor.process(base);
        handleResult(result);
    }

    private void processNext() throws MalformedURLException {
        if (!toProcess.isEmpty()) {
            PageUrls result = processor.process(new URL(toProcess.poll()));
            handleResult(result);
        }
    }

    private void handleResult(PageUrls result) {
        if (result != null) {
            tryToAddForProcessing(result.getLinks());
            results.add(result);
        }
    }

    private void tryToAddForProcessing(Set<String> urls) {
        for (String url : urls)
            if (alreadyProcessed < 100) tryToAddFixed(url);
    }

    private void tryToAddFixed(String url) {
        String candidate = fixer.fixForCrawling(base, url);
        if (candidate != null && !uniqueProcessed.contains(candidate)) {
            toProcess.add(candidate);
            uniqueProcessed.add(candidate);
            alreadyProcessed++;
        }
    }

    public void setProcessor(PageProcessor processor) {
        this.processor = processor;
    }

    public void setFixer(UrlFixer fixer) {
        this.fixer = fixer;
    }
}
