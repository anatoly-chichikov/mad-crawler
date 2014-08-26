package madcrawler.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import madcrawler.crawling.PageProcessor;
import madcrawler.messages.Aggregate;
import madcrawler.messages.Crawl;
import madcrawler.messages.SubmitCrawl;
import madcrawler.url.PageUrls;

import static madcrawler.settings.Logger.log;

public class MadCrawler extends UntypedActor {

    @Inject private PageProcessor processor;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Crawl)
            crawlPage((Crawl) message);
    }

    private void crawlPage(Crawl message) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        PageUrls result = processor.process(message.getPage());
        log("%s processed during %s\n", message.getPage(), stopwatch);

        if (result != null)
            tellToAggregateSuccessful(message, result);

        message.getInspector().
                tell(new SubmitCrawl(), ActorRef.noSender());
    }

    private void tellToAggregateSuccessful(Crawl message, PageUrls result) {
        log("%s URLs found on %s\n",
                result.getLinks().size(),
                result.getPage());
        message.getAggregator().
                tell(new Aggregate(result), ActorRef.noSender());
    }
}
