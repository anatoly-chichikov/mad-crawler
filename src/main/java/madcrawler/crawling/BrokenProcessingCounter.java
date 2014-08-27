package madcrawler.crawling;

public class BrokenProcessingCounter {

    private int brokenIndex;

    public void reportGood() {
        if (brokenIndex > 0) brokenIndex--;
    }

    public void reportBad() {
        brokenIndex++;
    }

    public boolean isBroken() {
        return brokenIndex > 15;
    }
}
