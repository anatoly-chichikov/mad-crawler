package madcrawler.url;

import org.junit.Test;

import java.net.URI;
import java.net.URL;

import static madcrawler.url.UrlChecker.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UrlCheckerTest {

    @Test
    public void testHasProtocol() {
        String urlHttp = "http://test.me";
        String urlHttps = "https://test.me";
        String urlWithoutProtocol = "test.me";

        assertTrue(hasProtocol(urlHttp));
        assertTrue(hasProtocol(urlHttps));
        assertFalse(hasProtocol(urlWithoutProtocol));
    }

    @Test
    public void testAbsoluteExternal() throws Exception{
        URL baseUrl = new URL("http://test.me");
        URI valid = new URI("http://another.com/page/");
        URI invalid = new URI("http://test.me/page/");
        URI internal = new URI("internal/page/");

        assertTrue(isAbsoluteExternal(valid, baseUrl));
        assertFalse(isAbsoluteExternal(invalid, baseUrl));
        assertFalse(isAbsoluteExternal(internal, baseUrl));
    }

    @Test
    public void testAbsoluteInternal() throws Exception{
        URL baseUrl = new URL("http://test.me");
        URI valid = new URI("http://test.me/page/");
        URI invalid = new URI("http://another.com/page/");
        URI internal = new URI("internal/page/");

        assertTrue(isAbsoluteInternal(valid, baseUrl));
        assertFalse(isAbsoluteInternal(invalid, baseUrl));
        assertFalse(isAbsoluteInternal(internal, baseUrl));
    }

    @Test
    public void testValidProtocol() throws Exception{
        URI http = new URI("http://test.me/page/");
        URI https = new URI("https://another.com/page/");
        URI ftp = new URI("ftp://test.me");
        URI sftp = new URI("sftp://test.me");

        assertTrue(isValidProtocol(http));
        assertTrue(isValidProtocol(https));
        assertFalse(isValidProtocol(ftp));
        assertFalse(isValidProtocol(sftp));
    }

    @Test
    public void testFragmentPresence() throws Exception {
        URI with = new URI("http://test.me/page#link");
        URI without = new URI("http://test.me/page");
        URI withParams = new URI("http://test.me/page?one=1&two=2");

        assertTrue(isContainsFragment(with));
        assertFalse(isContainsFragment(without));
        assertFalse(isContainsFragment(withParams));
    }
}