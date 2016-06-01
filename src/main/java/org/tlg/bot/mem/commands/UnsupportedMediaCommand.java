/**
 * 
 */
package org.tlg.bot.mem.commands;

import org.telegram.telegrambots.TelegramApiException;
import org.tlg.bot.mem.MemBot;
import org.tlg.bot.mem.msg.TextMessage;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class UnsupportedMediaCommand extends ExecuteCommand implements Command {
    final Long chatId;
    
    protected UnsupportedMediaCommand(final MemBot bot, final Long chatId) {
        super(bot);
        this.chatId = chatId;
    }

    /* (non-Javadoc)
     * @see org.tlg.bot.mem.commands.Command#execute()
     */
    @Override
    public void execute() throws TelegramApiException {
        getBot().sendMessage(
            new TextMessage(
                this.chatId, "Sorry, such media type is not supported now"
                )
            );

    }

}
