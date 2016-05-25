package org.tlg.bot.mem;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.RequestResult;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.tlg.bot.mem.commands.Command;

public class MemBot extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory
        .getLogger(MemBot.class.getName());

    private final String name;
    private final String token;

    private final Map<Long, Command> commands = new HashMap<>();
    private final Map<RequestResult, Conversation> expectators = new HashMap<>();
    

    public MemBot (final String name, final String token) {
        this.name = name;
        this.token = token;
    }
    
    @Override
    public void onUpdateReceived(final RequestResult result) {
        log.debug("result:{}", result);
        if (result.isOk()) {
            conversation(result);
        }

    }


    private void conversation(final RequestResult result) {
        //if somebody waits answer
        final Conversation conversation = getExpectator(result);
        conversation.process(result);
       
    }


    private Conversation getExpectator(final RequestResult result) {
        if (!this.expectators.containsKey(result)) {
            
            return new Conversation(this, result);
        } else {
            final Conversation conversation = this.expectators.get(result);
            this.expectators.remove(result);
            return conversation;
        }
    }




    /**
     * Invoked if there is a expectant in queue
     * @param command
     * @param update
     */
    void resume(final Command command, final Update update) {
        this.commands.remove(update.getMessage().getChatId());
        command.resume(this, update);
    }

    /**
     * Execute this command as result of processing {@code Update}.
     * @param command
     */
    void execute(final Command command) {
        command.execute(this);
    }
    
    public void answerAwait(final Long chatId, final Command waitor) {
        this.commands.put(chatId, waitor);
    }

    @Override
    public String getBotUsername() {
        return this.name;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }


    public void answerAwait(final Conversation conversation, final RequestResult requestResult) {
        this.expectators.put(requestResult, conversation);
    }

}
