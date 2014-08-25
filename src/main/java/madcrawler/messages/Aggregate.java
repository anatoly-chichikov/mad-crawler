package madcrawler.messages;

import madcrawler.url.PageUrls;

public class Aggregate {

    private final PageUrls urls;

    public Aggregate(final PageUrls urls) {
        this.urls = urls;
    }

    public PageUrls getUrls() {
        return urls;
    }
}
