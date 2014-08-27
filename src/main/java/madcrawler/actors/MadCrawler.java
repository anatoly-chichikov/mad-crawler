package madcrawler.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import madcrawler.crawling.RecursiveProcessor;
import madcrawler.messages.Aggregate;
import madcrawler.messages.Crawl;
import madcrawler.messages.SubmitCrawl;
import madcrawler.url.PageUrls;

import static madcrawler.settings.Logger.log;

public class MadCrawler extends UntypedActor {

    @Inject private RecursiveProcessor processor;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Crawl)
            crawlPage((Crawl) message);
    }

    private void crawlPage(Crawl message) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        processor.charge(message.getPage());

        for (PageUrls urls : processor) {
            log("%s processed during %s\n", message.getPage(), stopwatch);
            if (urls != null) {
               tellToAggregateSuccessful(message, urls);
            }
            stopwatch.reset();
            stopwatch.start();
        }

        message.getInspector().
                tell(new SubmitCrawl(), ActorRef.noSender());
    }

    private void tellToAggregateSuccessful(Crawl message, PageUrls result) {
        message.getAggregator().
                tell(new Aggregate(result), ActorRef.noSender());
    }
}
