/**
 * 
 */
package org.tlg.bot.mem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.RequestResult;
import org.telegram.telegrambots.api.objects.Update;
import org.tlg.bot.mem.commands.Command;
import org.tlg.bot.mem.commands.SearchMediaCommand;
import org.tlg.bot.mem.proc.CommandProcessor;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class Conversation {

    private static final Logger log = LoggerFactory
        .getLogger(Conversation.class.getName());
    
    private final MemBot bot;
    private final RequestResult requestResult;
    private final List<RequestResult> answers;

    private final Integer chatId;
    public Conversation(final MemBot bot, final RequestResult requestResult) {
        this.bot = bot;
        this.requestResult = requestResult;
        this.answers = new ArrayList<>();
        this.chatId = requestResult.chatId();
        bot.answerAwait(this, requestResult);

    }

    public void process(final RequestResult result) {
        // if this is first request in this conversation
        if (isStartOfConversation(result)) {
            start(result);
        } else {
            resume(result);
        }
    }

    private void resume(final RequestResult result) {
        log.debug("resume:{}", result);
        
    }

    private void start(final RequestResult result) {
        
        //if this just text 
        final Collection<Update> updates = result.updates();
        if (updates.size() == 1) {
            final Update update = updates.iterator().next();
            if (update.hasMessage()) {
                processAsMessage(update);
            }
            return;
        }
        
        //else ask for tags
        this.bot.answerAwait(this, result);
//        if (updates.size() > 0) {
//            final Update update = updates.iterator().next();
//            if (update.hasMessage()) {
//                processAsMessage(update);
//            }
//        }
        //if some media for storage - start saving
        
        
    }

    private boolean isStartOfConversation(final RequestResult result) {
        return !answers.contains(result);
    }

    private void processAsInlineQuery(final Update update) {
        if (update.getInlineQuery().getQuery() != null
            && !update.getInlineQuery().getQuery().isEmpty()) {
            this.bot.execute(new SearchMediaCommand(update.getInlineQuery()));
        }

    }

    private void processAsMessage(final Update update) {
        final Command command = new CommandProcessor(update).command();
        this.bot.execute(command);

    }
}
