package madcrawler.crawling;

import madcrawler.url.PageUrls;
import madcrawler.url.UrlFixer;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecursiveProcessorTest {

    @Test
    public void testGeneralBehavior() throws MalformedURLException {
        URL base = new URL("http://test.me");
        RecursiveProcessor testedProcessor = new RecursiveProcessor();
        PageProcessor mockedPageProcessor = mock(PageProcessor.class);
        TimeController mockedTimeController = mock(TimeController.class);

        testedProcessor.setFixer(new UrlFixer());
        testedProcessor.setProcessor(mockedPageProcessor);
        testedProcessor.setTimeController(mockedTimeController);
        testedProcessor.setCounter(new BrokenProcessingCounter());
        when(mockedPageProcessor.process(base)).thenReturn(
                new PageUrls(base,
                        newHashSet(
                                "http://test.me/one",
                                "/another-one",
                                "http://google.ru")));

        Set<PageUrls> resultSet = newHashSet();

        testedProcessor.charge(base);
        for (PageUrls processed : testedProcessor)
            if (processed != null)
                resultSet.add(processed);

        assertThat(resultSet.size(), is(1));
        assertThat(resultSet.iterator().next(),
                is(new PageUrls(base,
                        newHashSet(
                                "http://test.me/one",
                                "/another-one",
                                "http://google.ru"))));
    }
}