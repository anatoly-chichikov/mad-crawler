package madcrawler.url;

import java.net.URL;

import static com.google.common.net.UrlEscapers.urlFragmentEscaper;
import static madcrawler.url.UrlChecker.*;

public class UrlFixer {

    public String fixForStoring(URL base, String toFix) {
        String candidate = toFix.trim();

        if (isAbsoluteValid(candidate)) return esc(candidate);
        if (isWithSlash(candidate)) return withSlashRelative(base, candidate);
        if (hasProtocol(candidate)) return null;
        if (isNotToPage(candidate)) return null;
        return withoutSlashRelative(base, candidate);
    }

    public String fixForCrawling(URL base, String toFix) {
        String candidate = toFix.trim();

        if (isAbsoluteValidInternal(base, candidate)) return esc(candidate);
        if (isWithSlash(candidate)) return withSlashRelative(base, candidate);
        if (isInternal(base, candidate)) return null;
        if (hasProtocol(candidate)) return null;
        if (isNotToPage(candidate)) return null;
        return withoutSlashRelative(base, candidate);
    }

    private boolean isAbsoluteValid(String candidate) {
        return hasProtocol(candidate) && isAbsValidProtocol(candidate);
    }

    private boolean isAbsoluteValidInternal(URL base, String candidate) {
        return hasProtocol(candidate) &&
                isAbsValidProtocol(candidate) &&
                isInternal(base, candidate);
    }

    private String withSlashRelative(URL base, String candidate) {
        return esc(base.getProtocol() + "://" +
                base.getHost() + candidate);
    }

    private String withoutSlashRelative(URL base, String candidate) {
        return esc(base.getProtocol() + "://" +
                base.getHost() + "/" + candidate);
    }

    private String esc(String toEsc) {
        return urlFragmentEscaper().escape(toEsc);
    }
}
