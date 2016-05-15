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
public abstract class ExecuteCommand implements Command {

    @Override
    public abstract void execute(MemBot sender);

    @Override
    public final void resume(final MemBot sender, final Update update) {
        throw new UnsupportedOperationException(
            "Resume operation is not supported"
            );
        
    }

}
