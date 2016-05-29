/**
 * 
 */
package org.tlg.bot.mem.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.tlg.bot.mem.MemBot;
import org.tlg.bot.mem.db.RepTags;
import org.tlg.bot.mem.db.domain.Picture;
import org.tlg.bot.mem.db.domain.Tags;
import org.tlg.bot.mem.db.ds.DsHikari;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class SearchMediaCommand extends  ExecuteCommand {

    private static final Logger log = LoggerFactory
        .getLogger(SearchMediaCommand.class.getName());
    
    private final InlineQuery query;
    
    public SearchMediaCommand(final MemBot bot, final InlineQuery query) {
        super(bot);
        this.query = query;
    }


    /* (non-Javadoc)
     * @see org.tlg.bot.mem.commands.Command#execute(org.tlg.bot.mem.MemBot)
     */
    @Override
    public void execute() {
        final Integer userId = query.getFrom().getId();
        try {
            final Collection<Picture> photos = new RepTags(DsHikari.ds())
                .findByTags(new Tags(query.getQuery()), userId);
            log.debug(
                "Found photos for user [{}] with tags [{}]:{}",
                userId, query.getQuery(), photos.size());
            if (photos.isEmpty()) {
                return;
            }
            final AnswerInlineQuery answer = new AnswerInlineQuery();
            
            answer.setInlineQueryId(query.getId());
            final List<InlineQueryResult> results = new ArrayList<>();
            photos.forEach(picture -> {
               results.add(
                   new InlineQueryResultCachedTlgMedia(picture).result()
                   );
            });
            answer.setResults(results );
            
            log.debug("answer:{}", answer.toJson());
            getBot().answerInlineQuery(answer);
        } catch (final SQLException e) {
            log.debug("Can't found photos", e);
        } catch (final TelegramApiException e) {
            log.debug(e.getApiResponse(), e);
        }
    }

}
