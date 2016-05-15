/**
 * 
 */
package org.tlg.bot.mem.commands;

import org.telegram.telegrambots.api.objects.Update;
import org.tlg.bot.mem.MemBot;

/**
 * Received command.
 * @author "Maksim Vakhnik"
 *
 */
public interface Command {

    void execute(MemBot sender);

    void resume(MemBot sender, Update update);

}
