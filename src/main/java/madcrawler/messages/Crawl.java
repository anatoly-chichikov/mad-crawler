package madcrawler.messages;

import akka.actor.ActorRef;

import java.net.URL;

public class Crawl {

    private final URL page;
    private final ActorRef aggregator;
    private final ActorRef inspector;

    public Crawl(final URL page, final ActorRef aggregator, final ActorRef inspector) {
        this.page = page;
        this.aggregator = aggregator;
        this.inspector = inspector;
    }

    public URL getPage() {
        return page;
    }

    public ActorRef getAggregator() {
        return aggregator;
    }

    public ActorRef getInspector() {
        return inspector;
    }
}
