/**
 * 
 */
package org.tlg.bot.mem.commands;

import org.telegram.telegrambots.api.objects.Update;
import org.tlg.bot.mem.MemBot;

/**
 * Command without resume
 * @author "Maksim Vakhnik"
 *
 */
public abstract class ExecuteCommand extends MemBotCommand {

    protected ExecuteCommand(final MemBot bot) {
        super(bot);
    }

    @Override
    public final void resume(final Update update) {
        throw new UnsupportedOperationException(
            "Resume operation is not supported"
            );
        
    }

}
