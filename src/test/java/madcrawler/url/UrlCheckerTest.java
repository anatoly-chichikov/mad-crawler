package madcrawler.url;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static madcrawler.url.UrlChecker.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UrlCheckerTest {

    @Test
    public void testNotUrl() {
        String jsCall = "javascript:console.log('test')";
        String mail = "mailto:test@test.me";
        String backPath = "../../dummy/path";
        String normal = "http://test.me";

        assertFalse(isNotToPage(normal));
        assertTrue(isNotToPage(mail));
        assertTrue(isNotToPage(jsCall));
        assertTrue(isNotToPage(backPath));
    }

    @Test
    public void testProtocolPresence() {
        String with = "http://test.me";
        String without = "page/";

        assertTrue(hasProtocol(with));
        assertFalse(hasProtocol(without));
    }

    @Test
    public void testAbsValidProtocol() {
        String http = "http://test.me/page/";
        String https = "https://another.com/page/";
        String ftp = "ftp://test.me";
        String sftp = "sftp://test.me";

        assertTrue(isAbsValidProtocol(http));
        assertTrue(isAbsValidProtocol(https));
        assertFalse(isAbsValidProtocol(ftp));
        assertFalse(isAbsValidProtocol(sftp));
    }

    @Test
    public void testFragmentPresence() {
        String with = "http://test.me/page#link";
        String without = "http://test.me/page";

        assertTrue(isContainsFragment(with));
        assertFalse(isContainsFragment(without));
    }

    @Test
    public void testSlashPresence() {
        String pathWith = "/page/one/";
        String pathWithout = "page/one/";

        assertTrue(isWithSlash(pathWith));
        assertFalse(isWithSlash(pathWithout));
    }

    @Test
    public void testInternal() throws MalformedURLException {
        URL base = new URL("http://test.me");

        String internal = "http://test.me/one/";
        String withSubDomain = "http://www.test.me/one/";
        String external = "http://www.not-test.me/one/";

        assertTrue(isInternal(base, internal));
        assertTrue(isInternal(base, withSubDomain));
        assertFalse(isInternal(base, external));
    }
}