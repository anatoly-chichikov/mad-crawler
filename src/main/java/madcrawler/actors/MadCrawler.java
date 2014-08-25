package madcrawler.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
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
        PageUrls result = processor.process(message.getPage());

        if (result != null)
            tellToAggregateSuccessful(message, result);

        message.getInspector().
                tell(new SubmitCrawl(), ActorRef.noSender());
    }

    private void tellToAggregateSuccessful(Crawl message, PageUrls result) {
        message.getAggregator().
                tell(new Aggregate(result), ActorRef.noSender());
        log(result);
    }
}
