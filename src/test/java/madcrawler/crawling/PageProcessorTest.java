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
        String mockedPage = "<body>" +
                "<div>" +
                "    <a href='http://test.me'></a>" +
                "    <a href='http://test.me/tests'></a>" +
                "    <a href='/tests/about'></a>" +
                "    <a href='page#look'></a>" +
                "    <a href='mailto:email@test.me'></a> " +
                "</div>" +
                "</body>" +
                "</html>";

        PageDownloader mockedDownloader = mock(PageDownloader.class);
        URL baseUrl = new URL("http://test.me");

        when(mockedDownloader.fetchPage(baseUrl)).
                thenReturn(Jsoup.parse(mockedPage));

        Set<String> expected = newHashSet(
               "http://test.me",
               "http://test.me/tests",
               "/tests/about",
               "page#look",
               "mailto:email@test.me");

        PageProcessor processor = new PageProcessor();
        processor.setDownloader(mockedDownloader);

        PageUrls result = processor.process(new URL("http://test.me"));

        assertThat(result.getPage(), is(baseUrl));
        assertThat(result.getLinks(), is(expected));
    }
}