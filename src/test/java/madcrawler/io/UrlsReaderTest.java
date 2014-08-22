package madcrawler.io;

import madcrawler.url.UrlChecker;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UrlsReaderTest {

    @Test
    public void testGettingUrlsFromFile() throws Exception {
        Set<URL> expectedSet = newHashSet(
            new URL("http://www.test.com"),
            new URL("http://anothertest.com"),
            new URL("http://test.me"));

        File testSource = new File(this.getClass().
            getResource("/valid-source.txt").getFile());

        UrlsReader testReader = new UrlsReader();
        testReader.setChecker(new UrlChecker());

        assertThat(testReader.getUrlsFromFile(testSource), is(expectedSet));
    }
}