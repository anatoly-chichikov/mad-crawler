package madcrawler.url;

import java.net.URL;

import static com.google.common.net.UrlEscapers.urlFragmentEscaper;
import static madcrawler.url.UrlChecker.*;

public class UrlFixer {

    public String fixAsString(URL base, String toFix) {
        String candidate = toFix.trim();

        if (hasProtocol(toFix))
            if (isAbsValidProtocol(candidate)) return esc(candidate);
            else return null;

        if (isNotToPage(candidate)) return null;
        if (isWithSlash(candidate)) return esc(base.toString() + candidate);

        return esc(base.toString() + '/' + candidate);
    }

    private String esc(String toEsc) {
        return urlFragmentEscaper().escape(toEsc);
    }
}
