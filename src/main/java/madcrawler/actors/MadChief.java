package madcrawler.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.google.inject.Inject;
import madcrawler.io.UrlsReader;
import madcrawler.messages.Crawl;
import madcrawler.messages.Expected;
import madcrawler.messages.Start;

import java.net.URL;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static madcrawler.settings.Actors.*;
import static madcrawler.settings.ExecutionTime.markStartPoint;
import static madcrawler.settings.Logger.log;

public class MadChief extends UntypedActor {

    private final ActorRef aggregator = getAggregator();
    private final ActorRef inspector = getInspector();
    private final List<ActorRef> crawlers = newArrayList();

    @Inject private UrlsReader reader;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Start)
            start((Start) message);
    }

    private void start(Start message) {
        Set<URL> toProcess = reader.
                getUrlsFromFile(message.getPath());

        markStartPoint();
        log("Source urls quantity: %s pcs\n", toProcess.size());

        tellExpectedUrlsCount(toProcess.size());
        initCrawlers(toProcess.size());
        chargeCrawlers(toProcess);
    }

    private void tellExpectedUrlsCount(int size) {
        inspector.tell(new Expected(size, aggregator), ActorRef.noSender());
    }

    private void initCrawlers(int urlsCount) {
        int maxActorsCount = 100;
        int crawlersCount = urlsCount < maxActorsCount ? urlsCount : maxActorsCount;
        for (int i = 0; i < crawlersCount; i++)
            crawlers.add(getCrawler());
    }

    private void chargeCrawlers(Set<URL> targets) {
        int actorsCount = 0;
        for (URL target : targets) {
            if (crawlers.size() == actorsCount) actorsCount = 0;
            tellCrawlerToStart(crawlers.get(actorsCount), target);
            actorsCount++;
        }
    }

    private void tellCrawlerToStart(ActorRef crawler, URL target) {
        crawler.tell(new Crawl(target, aggregator, inspector),
                ActorRef.noSender());
    }
}
