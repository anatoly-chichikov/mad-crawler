package madcrawler.url;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class UrlFixerTest {

    URL base;
    UrlFixer fixer;

    @Before
    public void init() throws Exception {
        base = new URL("http://test.me");
        fixer = new UrlFixer();
    }

    @Test
    public void testRelativeUrlFixForStoring() {
        String one = "/one";
        String two = "two";

        assertThat(fixer.fixForStoring(base, one),
                is("http://test.me/one"));
        assertThat(fixer.fixForStoring(base, two),
                is("http://test.me/two"));
    }

    @Test
    public void testWithProtocolFixForStoring() {
        String http = "http://test.me";
        String https = "https://test.me";
        String ftp = "ftp://test.me";

        assertThat(fixer.fixForStoring(base, http),
                is("http://test.me"));
        assertThat(fixer.fixForStoring(base, https),
                is("https://test.me"));
        assertNull(fixer.fixForStoring(base, ftp));
    }

    @Test
    public void testNotPageFixForStoring() {
        String jsCall = "javascript:console.log('test')";
        String mail = "mailto:test@test.me";
        String backPath = "../../dummy/path";

        assertNull(fixer.fixForStoring(base, jsCall));
        assertNull(fixer.fixForStoring(base, mail));
        assertNull(fixer.fixForStoring(base, backPath));
    }

    @Test
    public void testRelativeFixForCrawling() throws Exception {
        String one = "/one";
        String two = "two";

        assertThat(fixer.fixForCrawling(base, one),
                is("http://test.me/one"));
        assertThat(fixer.fixForCrawling(base, two),
                is("http://test.me/two"));
    }

    @Test
    public void testAbsoluteFixForCrawling() throws Exception {
        String one = "http://test.me/one";
        String two = "http://www.test.me/two";
        String external = "http://not-test.me/please";

        assertThat(fixer.fixForCrawling(base, one),
                is("http://test.me/one"));
        assertThat(fixer.fixForCrawling(base, two),
                is("http://www.test.me/two"));
        assertNull(fixer.fixForCrawling(base, external));
    }

    @Test
    public void testWithProtocolFixForCrawling() {
        String http = "http://test.me";
        String https = "https://test.me";
        String ftp = "ftp://test.me";

        assertThat(fixer.fixForCrawling(base, http),
                is("http://test.me"));
        assertThat(fixer.fixForCrawling(base, https),
                is("https://test.me"));
        assertNull(fixer.fixForCrawling(base, ftp));
    }

    @Test
    public void testNotPageFixForCrawling() {
        String jsCall = "javascript:console.log('test')";
        String mail = "mailto:test@test.me";
        String backPath = "../../dummy/path";

        assertNull(fixer.fixForCrawling(base, jsCall));
        assertNull(fixer.fixForCrawling(base, mail));
        assertNull(fixer.fixForCrawling(base, backPath));
    }
}