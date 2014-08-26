package madcrawler.settings;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import madcrawler.actors.MadAggregator;
import madcrawler.actors.MadChief;
import madcrawler.actors.MadCrawler;
import madcrawler.actors.MadInspector;

import static madcrawler.App.getInjector;

public class Actors {

    private Actors() {
        throw new CrawlerException("You should not instantiate this class");
    }

    private static ActorSystem system = ActorSystem.
            create("MadCrawler");

    public static ActorRef getChief() {
        return fromGenericPool(MadChief.class);
    }

    public static ActorRef getInspector() {
        return fromGenericPool(MadInspector.class);
    }

    public static ActorRef getAggregator() {
        return fromGenericPool(MadAggregator.class);
    }

    public static ActorRef getCrawler() {
        return fromExtendedPool(MadCrawler.class);
    }

    public static void shutdownApp() {
        system.shutdown();
    }

    private static ActorRef fromGenericPool(Class type) {
        return system.actorOf(
                Props.create(
                        ActorsInjector.class,
                        getInjector(),
                        type).withDispatcher("core-dispatcher"));
    }

    private static ActorRef fromExtendedPool(Class type) {
        return system.actorOf(
                Props.create(
                        ActorsInjector.class,
                        getInjector(),
                        type).withDispatcher("crawler-dispatcher"));
    }
}
