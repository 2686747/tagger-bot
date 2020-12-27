/**
 *
 */
package org.tlg.bot.mem.msg;

import org.tlg.bot.mem.tools.ResourceReader;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class VersionMessage extends TextMessage {

    private static final String VERSION = "Version";

    public VersionMessage(final Long chatId) {
        super(chatId, VersionMessage.version());

    }

    private static String version() {
        return ResourceReader.readManifest(VERSION).orElse("latest");
    }

}
