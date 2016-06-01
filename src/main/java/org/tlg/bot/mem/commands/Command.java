/**
 * 
 */
package org.tlg.bot.mem.commands;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Received command.
 * @author "Maksim Vakhnik"
 *
 */
public interface Command {

    void execute() throws TelegramApiException;

    void resume(Update update);

}
