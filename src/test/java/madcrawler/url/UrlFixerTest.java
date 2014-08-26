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
    public void testRelativeUrlFixAsString() {
        String one = "/one";
        String two = "two";

        assertThat(fixer.fixAsString(base, one),
                is("http://test.me/one"));
        assertThat(fixer.fixAsString(base, two),
                is("http://test.me/two"));
    }

    @Test
    public void testWithProtocolFixAsString() {
        String http = "http://test.me";
        String https = "https://test.me";
        String ftp = "ftp://test.me";

        assertThat(fixer.fixAsString(base, http),
                is("http://test.me"));
        assertThat(fixer.fixAsString(base, https),
                is("https://test.me"));
        assertNull(fixer.fixAsString(base, ftp));
    }

    @Test
    public void testNotPage() {
        String jsCall = "javascript:console.log('test')";
        String mail = "mailto:test@test.me";
        String backPath = "../../dummy/path";

        assertNull(fixer.fixAsString(base, jsCall));
        assertNull(fixer.fixAsString(base, mail));
        assertNull(fixer.fixAsString(base, backPath));
    }
}