package madcrawler.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.google.inject.Inject;
import madcrawler.crawling.RecursiveProcessor;
import madcrawler.messages.Aggregate;
import madcrawler.messages.Crawl;
import madcrawler.messages.SubmitCrawl;
import madcrawler.url.PageUrls;

public class MadCrawler extends UntypedActor {

    @Inject private RecursiveProcessor processor;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Crawl)
            crawlPage((Crawl) message);
    }

    private void crawlPage(Crawl message) {
        crawlRecursively(message);
        tellAboutEnd(message);
    }

    private void crawlRecursively(Crawl message) {
        processor.charge(message.getPage());
        for (PageUrls urls : processor)
            if (urls != null) tellToAggregateSuccessful(message, urls);
    }

    private void tellToAggregateSuccessful(Crawl message, PageUrls result) {
        message.getAggregator().
                tell(new Aggregate(result), ActorRef.noSender());
    }

    private void tellAboutEnd(Crawl message) {
        message.getInspector().
                tell(new SubmitCrawl(), ActorRef.noSender());
    }
}
