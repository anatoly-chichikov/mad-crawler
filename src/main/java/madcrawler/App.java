package madcrawler;

import com.google.inject.Guice;
import com.google.inject.Injector;
import madcrawler.settings.Injections;

public class App {

    public static void main(String[] args) {
        System.out.println("Mad Crawler app has been started");
        getInjectedApp().start();
        System.out.println("Mad Crawler app has been finished");
    }

    private static App getInjectedApp() {
        Injector injector = Guice.createInjector(new Injections());
        return injector.getInstance(App.class);
    }

    private void start() {
        System.out.println("Doing work...");
    }
}
