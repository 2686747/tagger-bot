/**
 * 
 */
package org.tlg.bot.mem.db.domain;

import java.util.Base64;

/**
 * Url of PageLink
 * @author "Maksim Vakhnik"
 *
 */
public class PageUrl {

    private final PageLink pageLink;

    public PageUrl(final PageLink pageLink) {
        this.pageLink = pageLink;
    }

    public String getUrl() {
        return PageUrl.url(pageLink);
    }

    /**
     * Creates short url from pageLink
     * @param pageLink
     * @return
     */
    private static String url(final PageLink pageLink) {
        return Base64.getUrlEncoder().encodeToString(
            (
                String.valueOf(pageLink.getUserId()) + 
                String.valueOf(pageLink.getCreated())
            ).getBytes()
        );
    }
}
