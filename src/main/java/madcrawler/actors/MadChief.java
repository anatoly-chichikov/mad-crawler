package madcrawler.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.google.inject.Inject;
import madcrawler.crawling.PageProcessor;
import madcrawler.io.UrlsReader;
import madcrawler.messages.Aggregate;
import madcrawler.messages.Finish;
import madcrawler.messages.Start;
import madcrawler.url.PageUrls;

import java.net.URL;
import java.util.Set;

import static madcrawler.settings.Actors.getAggregator;
import static madcrawler.settings.ExecutionTime.markStartPoint;
import static madcrawler.settings.Logger.log;

public class MadChief extends UntypedActor {

    @Inject private UrlsReader reader;
    @Inject private PageProcessor processor;
    private ActorRef aggregator = getAggregator();


    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Start)
            start((Start) message);
    }

    private void start(Start message) {
        Set<URL> toProcess = reader.
                getUrlsFromFile(message.getPath());

        markStartPoint();
        log("Source urls: %s pcs\n", toProcess.size());

        for (URL target : toProcess)
            processUrl(target);

        aggregator.tell(new Finish(), ActorRef.noSender());
    }

    private void processUrl(URL target) {
        PageUrls result = processor.process(target);
        if (result != null) log(result);
        aggregator.tell(new Aggregate(result), ActorRef.noSender());
    }
}
