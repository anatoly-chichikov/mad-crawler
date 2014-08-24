package madcrawler.actors;

import akka.actor.UntypedActor;
import com.google.inject.Inject;
import madcrawler.crawling.PageProcessor;
import madcrawler.io.ResultAggregator;
import madcrawler.io.UrlsReader;
import madcrawler.messages.Start;
import madcrawler.url.PageUrls;

import java.net.URL;
import java.util.Set;

import static madcrawler.settings.Actors.shutdownApp;
import static madcrawler.settings.Logger.log;

public class MadChief extends UntypedActor {

    @Inject private UrlsReader reader;
    @Inject private PageProcessor processor;
    @Inject private ResultAggregator aggregator;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Start) {
            log("Mad Crawler app has been started");
            handleStartMessage((Start) message);
            log("Mad Crawler app has been finished");
        }
    }

    private void handleStartMessage(Start message) {
        Set<URL> toProcess = reader.
                getUrlsFromFile(message.getPath());
        log("Source urls: %s pcs\n", toProcess.size());
        for (URL target : toProcess)
            processUrl(target);
        aggregator.writeResultFile();
        shutdownApp();
    }

    private void processUrl(URL target) {
        PageUrls result = processor.process(target);
        if (result != null) log(result);
        aggregator.add(result);
    }
}
