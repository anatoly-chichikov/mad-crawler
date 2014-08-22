package madcrawler.url;

import org.junit.Test;

import java.net.URI;
import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UrlCheckerTest {

    UrlChecker checker = new UrlChecker();

    @Test
    public void testHasProtocol() {
        String urlHttp = "http://test.me";
        String urlHttps = "https://test.me";
        String urlWithoutProtocol = "test.me";

        assertTrue(checker.hasProtocol(urlHttp));
        assertTrue(checker.hasProtocol(urlHttps));
        assertFalse(checker.hasProtocol(urlWithoutProtocol));
    }

    @Test
    public void testAbsoluteExternal() throws Exception{
        URL baseUrl = new URL("http://test.me");
        URI valid = new URI("http://another.com/page/");
        URI invalid = new URI("http://test.me/page/");
        URI internal = new URI("internal/page/");

        assertTrue(checker.isAbsoluteExternal(valid, baseUrl));
        assertFalse(checker.isAbsoluteExternal(invalid, baseUrl));
        assertFalse(checker.isAbsoluteExternal(internal, baseUrl));
    }

    @Test
    public void testAbsoluteInternal() throws Exception{
        URL baseUrl = new URL("http://test.me");
        URI valid = new URI("http://test.me/page/");
        URI invalid = new URI("http://another.com/page/");
        URI internal = new URI("internal/page/");

        assertTrue(checker.isAbsoluteInternal(valid, baseUrl));
        assertFalse(checker.isAbsoluteInternal(invalid, baseUrl));
        assertFalse(checker.isAbsoluteInternal(internal, baseUrl));
    }

    @Test
    public void testValidProtocol() throws Exception{
        URI http = new URI("http://test.me/page/");
        URI https = new URI("https://another.com/page/");
        URI ftp = new URI("ftp://test.me");
        URI sftp = new URI("sftp://test.me");

        assertTrue(checker.isValidProtocol(http));
        assertTrue(checker.isValidProtocol(https));
        assertFalse(checker.isValidProtocol(ftp));
        assertFalse(checker.isValidProtocol(sftp));
    }

    @Test
    public void testFragmentPresence() throws Exception {
        URI with = new URI("http://test.me/page#link");
        URI without = new URI("http://test.me/page");
        URI withParams = new URI("http://test.me/page?one=1&two=2");

        assertTrue(checker.isContainsFragment(with));
        assertFalse(checker.isContainsFragment(without));
        assertFalse(checker.isContainsFragment(withParams));
    }
}