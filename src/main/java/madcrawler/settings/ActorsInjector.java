package madcrawler.settings;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import com.google.inject.Injector;

public class ActorsInjector implements IndirectActorProducer {

    final Injector injector;
    final Class<? extends Actor> actorClass;

    public ActorsInjector(Injector injector, Class<? extends Actor> actorClass) {
        this.injector = injector;
        this.actorClass = actorClass;
    }

    @Override
    public Actor produce() {
        return injector.getInstance(actorClass);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return actorClass;
    }
}
