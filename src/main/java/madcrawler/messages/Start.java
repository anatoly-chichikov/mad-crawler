package madcrawler.messages;

public class Start {

    private final String path;

    public Start(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
