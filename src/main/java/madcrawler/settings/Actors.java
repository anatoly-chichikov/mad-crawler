package madcrawler.settings;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import madcrawler.actors.MadChief;

import static madcrawler.App.getInjector;

public class Actors {

    private  static ActorSystem system = ActorSystem.
            create("MadCrawler");

    public static ActorRef getChief() {
        return system.actorOf(
                Props.create(
                        ActorsInjector.class,
                        getInjector(),
                        MadChief.class), "chief");
    }

    public static void shutdownApp() {
        system.shutdown();
    }
}
