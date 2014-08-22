package madcrawler;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import madcrawler.io.UrlsReader;
import madcrawler.settings.CrawlerException;
import madcrawler.settings.Injections;

import static com.google.common.base.Joiner.on;
import static madcrawler.settings.Logger.log;

public class App {

    private UrlsReader reader;

    private void start(String source) {
        log("Source urls: \n\t%s", on("\n\t").join(reader.getUrlsFromFile(source)));
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
}
