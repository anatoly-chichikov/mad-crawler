package madcrawler.settings;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import madcrawler.actors.MadAggregator;
import madcrawler.actors.MadChief;

import static madcrawler.App.getInjector;

public class Actors {

    private Actors() {
        throw new CrawlerException("You should not instantiate this class");
    }

    private static ActorSystem system = ActorSystem.
            create("MadCrawler");

    public static ActorRef getChief() {
        return getActor(MadChief.class);
    }

    public static ActorRef getAggregator() {
        return getActor(MadAggregator.class);
    }

    public static void shutdownApp() {
        system.shutdown();
    }

    private static ActorRef getActor(Class type) {
        return system.actorOf(
                Props.create(
                        ActorsInjector.class,
                        getInjector(),
                        type));
    }
}
