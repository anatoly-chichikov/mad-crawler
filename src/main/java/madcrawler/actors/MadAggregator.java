package madcrawler.actors;

import akka.actor.UntypedActor;
import com.google.inject.Inject;
import madcrawler.io.ResultAggregator;
import madcrawler.messages.Aggregate;
import madcrawler.messages.Finish;

import static madcrawler.settings.Actors.shutdownApp;
import static madcrawler.settings.ExecutionTime.markEndPoint;

public class MadAggregator extends UntypedActor {

    @Inject private ResultAggregator aggregator;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Aggregate)
            aggregate((Aggregate) message);

        if (message instanceof Finish)
            finish();
    }

    private void aggregate(Aggregate message) {
        aggregator.add(message.getUrls());
    }

    private void finish() {
        aggregator.writeResultFile();
        markEndPoint();
        shutdownApp();
    }
}
