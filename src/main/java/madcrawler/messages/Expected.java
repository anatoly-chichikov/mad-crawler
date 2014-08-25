package madcrawler.messages;

import akka.actor.ActorRef;

public class Expected {

    private final int quantity;
    private final ActorRef aggregator;

    public Expected(final int quantity, final ActorRef aggregator) {
        this.quantity = quantity;
        this.aggregator = aggregator;
    }

    public int getQuantity() {
        return quantity;
    }

    public ActorRef getAggregator() {
        return aggregator;
    }
}
