package madcrawler;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import madcrawler.crawling.PageProcessor;
import madcrawler.io.ResultAggregator;
import madcrawler.io.UrlsReader;
import madcrawler.settings.CrawlerException;
import madcrawler.settings.Injections;
import madcrawler.url.PageUrls;

import java.net.URL;
import java.util.Set;

import static madcrawler.settings.Logger.log;

public class App {

    private UrlsReader reader;
    private PageProcessor processor;
    private ResultAggregator aggregator;

    private void start(String source) {
        Set<URL> toProcess = reader.getUrlsFromFile(source);
        log("Source urls: %s pcs\n", toProcess.size());

        for (URL target : toProcess)
            processUrl(target);

        aggregator.writeResultFile();
    }

    private void processUrl(URL target) {
        PageUrls result = processor.process(target);
        if (result != null) log(result);
        aggregator.add(result);
    }

    public static void main(String[] args) {
        log("Mad Crawler app has been started");
        getInjectedApp().start(getFirstArgAsPath(args));
        log("Mad Crawler app has been finished");
    }

    private static App getInjectedApp() {
        Injector injector = Guice.createInjector(new Injections());
        return injector.getInstance(App.class);
    }

    private static String getFirstArgAsPath(String[] args) {
        if (args.length == 0)
            throw new CrawlerException("You should provide a source file as an argument");
        return args[0];
    }

    @Inject
    public void setReader(UrlsReader reader) {
        this.reader = reader;
    }

    @Inject
    public void setProcessor(PageProcessor processor) {
        this.processor = processor;
    }

    @Inject
    public void setAggregator(ResultAggregator aggregator) {
        this.aggregator = aggregator;
    }
}
