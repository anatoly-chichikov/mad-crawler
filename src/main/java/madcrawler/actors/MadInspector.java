package madcrawler.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import madcrawler.messages.Expected;
import madcrawler.messages.Finish;
import madcrawler.messages.SubmitCrawl;

public class MadInspector extends UntypedActor {

    private int expectedJobs = -1;
    private int doneJobs = 0;
    private ActorRef aggregator;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Expected)
            initExpectations((Expected) message);
        if (message instanceof SubmitCrawl)
            doneJobs++;
        if (expectedJobs == doneJobs)
            tellToFinishApp();
    }

    private void initExpectations(Expected message) {
        expectedJobs = message.getQuantity();
        aggregator = message.getAggregator();
    }

    private void tellToFinishApp() {
        aggregator.tell(new Finish(), ActorRef.noSender());
    }
}
