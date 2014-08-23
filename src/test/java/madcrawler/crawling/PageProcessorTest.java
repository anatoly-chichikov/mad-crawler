package madcrawler.crawling;

import madcrawler.url.PageUrls;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.net.URL;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PageProcessorTest {

    @Test
    public void testPageProcessing() throws Exception {
        String mockedPage = "<!DOCTYPE html>" +
            "<html>" +
            "<head lang='en'>" +
            "    <meta charset='UTF-8'>" +
            "    <title></title>" +
            "</head>" +
            "<body>" +
            "<div>" +
            "    <a href='http://test.me'></a>" +
            "    <a href='http://www.test.me/withprefix'></a>" +
            "    <a href='http://test.me/tests'></a>" +
            "    <a href='/tests/about'></a>" +
            "    <a href='page'></a>" +
            "    <a href='page#look'></a>" +
            "    <a href='http://google.com'></a>" +
            "    <a href='ftp://test.me'></a>" +
            "    <a href='mailto:email@test.me'></a> " +
            "</div>" +
            "</body>" +
            "</html>";

        PageDownloader mockedDownloader = mock(PageDownloader.class);
        URL baseUrl = new URL("http://test.me");

        when(mockedDownloader.fetchPage(baseUrl)).
            thenReturn(Jsoup.parse(mockedPage));

        Set<URL> expectedInternalsSet = newHashSet(
            new URL("http://test.me"),
            new URL("http://www.test.me/withprefix"),
            new URL("http://test.me/tests"),
            new URL("http://test.me/tests/about"),
            new URL("http://test.me/page")
        );
        Set<URL> expectedExternalsSet = newHashSet(
            new URL("http://google.com")
        );

        PageProcessor processor = new PageProcessor();
        processor.setDownloader(mockedDownloader);

        PageUrls result = processor.process(new URL("http://test.me"));

        assertThat(result.getPage(), is(baseUrl));
        assertThat(result.getInternalLinks(), is(expectedInternalsSet));
        assertThat(result.getExternalLinks(), is(expectedExternalsSet));
    }
}