package madcrawler.io;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.net.URL;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UrlsReaderTest {

    @Test
    public void testGettingUrlsFromFile() throws Exception {
        Set<URL> expectedSet = newHashSet(
            new URL("http://www.test.com"),
            new URL("http://anothertest.com"),
            new URL("http://test.me"));

        List<String> mockedFile = Lists.newArrayList(
            "www.test.com",
            "http://anothertest.com",
            "test.me");

        FileReader mockedFileReader = mock(FileReader.class);
        when(mockedFileReader.readUrlsAsLines("/test.txt")).thenReturn(mockedFile);

        UrlsReader testUrlReader = new UrlsReader();
        testUrlReader.setFileReader(mockedFileReader);

        assertThat(testUrlReader.getUrlsFromFile("/test.txt"), is(expectedSet));
    }
}