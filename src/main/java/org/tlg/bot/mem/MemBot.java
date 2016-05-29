package org.tlg.bot.mem;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.tlg.bot.mem.commands.Command;
import org.tlg.bot.mem.commands.SearchMediaCommand;
import org.tlg.bot.mem.proc.CommandProcessor;
import jersey.repackaged.com.google.common.base.Objects;

public class MemBot extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory
        .getLogger(MemBot.class.getName());

    private final String name;
    private final String token;
    private final Map<Long, Command> commands = new HashMap<>();

    public MemBot(final String name, final String token) {
        this.name = name;
        this.token = token;
    }

    @Override
    public void onUpdateReceived(final Update update) {
        log.debug("update:{}", update);
        // process if this somebody answer
        if (update.hasMessage()) {
            hideKeyboard(update.getMessage());
            processAsMessage(update);
        }
        if (update.hasInlineQuery()) {
            processAsInlineQuery(update);
        }

    }

    private void processAsInlineQuery(final Update update) {
        if (update.getInlineQuery().getQuery() != null
            && !update.getInlineQuery().getQuery().isEmpty()) {
            execute(new SearchMediaCommand(this, update.getInlineQuery()));
        }

    }

    private void processAsMessage(final Update update) {
        if (commands.containsKey(update.getMessage().getChatId())) {
            final Command command = commands
                .get(update.getMessage().getChatId());
            resume(command, update);
        } else {
            final Command command =
                new CommandProcessor(this, update).command();
            this.execute(command);
        }

    }

    /**
     * Invoked if there is a expectant in queue
     * 
     * @param command
     * @param update
     */
    void resume(final Command command, final Update update) {
        this.commands.remove(update.getMessage().getChatId());
        command.resume(update);
    }

    /**
     * Execute this command as result of processing {@code Update}.
     * 
     * @param command
     */
    void execute(final Command command) {
        command.execute();
    }

    public void leaveAwaitQueue(final Command command) {
        this.commands.forEach((chatId, cmd) -> {
            if (Objects.equal(command, cmd)) {
                this.commands.remove(chatId, command);
            }
        });

    }

    
    public void hideKeyboard(final Message message) {
//        try {
//            final SendMessage msg = new SendMessage();
//            msg.setChatId(String.valueOf(message.getChatId()));
//            final ReplyKeyboard hideKeyboard = new ReplyKeyboardHide();
//            msg.setReplayMarkup(hideKeyboard );
//            sendMessage(msg);
//        } catch (final TelegramApiException e) {
//            e.printStackTrace();
//        }
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

    @Override
    public Message sendMessage(final SendMessage sendMessage)
        throws TelegramApiException {
        log.debug("sendMessage:{}", sendMessage.toJson());
        return super.sendMessage(sendMessage);
    }

}
