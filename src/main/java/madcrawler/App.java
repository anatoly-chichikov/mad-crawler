package madcrawler;

import akka.actor.ActorRef;
import com.google.inject.Guice;
import com.google.inject.Injector;
import madcrawler.messages.Start;
import madcrawler.settings.Actors;
import madcrawler.settings.CrawlerException;
import madcrawler.settings.Injections;

public class App {

    private static Injector injector;

    private void start(String source) {
        ActorRef chef = Actors.getChief();
        chef.tell(new Start(source), ActorRef.noSender());
    }

    public static void main(String[] args) {
        getInjectedApp().start(getFirstArgAsPath(args));
    }

    public static Injector getInjector() {
        return injector;
    }

    private static App getInjectedApp() {
        injector = Guice.createInjector(new Injections());
        return injector.getInstance(App.class);
    }

    private static String getFirstArgAsPath(String[] args) {
        if (args.length == 0)
            throw new CrawlerException("You should provide a source file as an argument");
        return args[0];
    }
}
