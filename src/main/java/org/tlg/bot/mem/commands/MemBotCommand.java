/**
 * 
 */
package org.tlg.bot.mem.commands;

import org.tlg.bot.mem.MemBot;

/**
 * @author "Maksim Vakhnik"
 *
 */
public abstract class MemBotCommand implements Command{

    private final MemBot bot;

    protected MemBotCommand(final MemBot bot) {
        super();
        this.bot = bot;
    }

    public final MemBot getBot() {
        return bot;
    }

}
