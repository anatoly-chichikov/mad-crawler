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
    @Inject private TimeController time;
    @Inject private BrokenProcessingCounter counter;

    private int alreadyProcessed;
    private boolean isUsed;
    private boolean isDisabled;

    private URL base;
    private Queue<PageUrls> results = newLinkedList();
    private Queue<String> targets = newLinkedList();
    private Set<String> uniqueProcessed = newHashSet();

    public void charge(URL base) {
        this.base = base;
    }

    @Override
    public boolean hasNext() {
        return (!isUsed || !targets.isEmpty()) && !isDisabled;
    }

    @Override
    public PageUrls next() {
        try {
            tryToProcessPage();
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

    private void tryToProcessPage() throws MalformedURLException {
        ensureCharging();
        if (!isUsed) processFirst();
        else processNext();
    }

    private void processFirst() {
        isUsed = true;
        uniqueProcessed.add(base.toString());
        PageUrls result = processor.process(base);
        time.mark();
        handleResult(result);
    }

    private void processNext() throws MalformedURLException {
        if (!targets.isEmpty()) {
            time.sleep();
            PageUrls result = processor.process(new URL(targets.poll()));
            time.mark();
            handleResult(result);
        }
    }

    private void handleResult(PageUrls result) {
        if (result != null) {
            counter.reportGood();
            addForFutureProcessing(result.getLinks());
            results.add(result);
        } else {
            counter.reportBad();
            checkCrawlingHealth();
        }
    }

    private void addForFutureProcessing(Set<String> urls) {
        if (alreadyProcessed < 99)
            for (String url : urls)
                if (alreadyProcessed < 99)
                    addPreparedLink(url);
    }

    private void addPreparedLink(String url) {
        String candidate = fixer.fixForCrawling(base, url);
        if (candidate != null && !uniqueProcessed.contains(candidate)) {
            targets.add(candidate);
            uniqueProcessed.add(candidate);
            alreadyProcessed++;
        }
    }

    private void checkCrawlingHealth() {
        isDisabled = counter.isBroken();
        if (isDisabled)
            log("Too many broken links for %s\nCrawling has been stopped", base);
    }

    private void ensureCharging() {
        if (base == null) throw new CrawlerException("Uncharged recursive processor");
    }

    public void setProcessor(PageProcessor processor) {
        this.processor = processor;
    }

    public void setFixer(UrlFixer fixer) {
        this.fixer = fixer;
    }

    public void setTimeController(TimeController time) {
        this.time = time;
    }

    public void setCounter(BrokenProcessingCounter counter) {
        this.counter = counter;
    }
}
