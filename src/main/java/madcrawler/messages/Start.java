package madcrawler.messages;

public class Start {

    private String path;

    public Start(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
