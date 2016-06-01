package org.tlg.bot.mem;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.concurrent.atomic.AtomicBoolean;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Update;
import org.tlg.bot.mem.commands.Command;
import org.tlg.bot.mem.commands.HelpCommand;
import org.tlg.bot.mem.commands.UnsupportedMediaCommand;

public class MemBotTest {

    private static final Logger log = LoggerFactory
        .getLogger(MemBotTest.class.getName());
    
	@Test
	public void testGetBotUsername() {
	    log.debug("username:{}", new AppConfig(App.KEY_NAME).value());
		MatcherAssert.assertThat(
			"Username is null or emtpy",
			new AppConfig(App.KEY_NAME).value(),
			Matchers.not(Matchers.isEmptyOrNullString())
		);
	}

	@Test
	public void testGetBotToken() {
	    log.debug("token:{}", new AppConfig(App.KEY_TOKEN).value());
	      MatcherAssert.assertThat(
	            "Username is null or emtpy",
	            new AppConfig(App.KEY_TOKEN).value(),
	            Matchers.not(Matchers.isEmptyOrNullString())
	        );
	}
	
	@Test
	public void unrecognizedTextIsHelpCommand() {
	    final AtomicBoolean succ = new AtomicBoolean(false);
	    final MemBot bot = new MemBot(
	        new AppConfig(App.KEY_NAME).value(),
	        new AppConfig(App.KEY_TOKEN).value()
	        ) {

            @Override
            void execute(final Command command) {
                Assert.assertTrue(
                    "Is not help command", command instanceof HelpCommand
                    );
                succ.set(true);
            }

	    };
	    
	    final JSONObject chat = new JSONObject()
            .put("id", 1)
            .put("type", "private");
	    final JSONObject message = new JSONObject()
	        .put("message_id", 1)
	        .put(
	            "date",
	            Long.valueOf(System.currentTimeMillis() / 1000L).intValue()
	            )
	        .put("chat", chat)
	        .put("text", "/help");
	    final JSONObject update = new JSONObject()
	        .put("update_id", 1)
	        .put("message", message);
	
	    final Update updateObj = new Update(update);
	    bot.onUpdateReceived(updateObj);
	    if (!succ.get()) {
	        fail("test is not passed");
	    }
	}
	
	@Test
	public void gifNotSupported() {
	    final AtomicBoolean succ = new AtomicBoolean(false);
	    final Update update = update("{'update_id':76454460,"
	        + "'message':{'date':1464811207,"
	        + "'chat':{'id':98470981,'type':'private','first_name':'Maksim'},"
	        + "'document':{"
	            + "'mime_type':'video/mp4',"
	            + "'thumb':{"
	                + "'file_id':'AAQEABN-AAAAAAAAAAAAAAAA',"
	                + "'width':90,"
	                + "'file_size':2233,"
	                + "'height':51},"
                + "'file_name':'giphy.mp4',"
                + "'file_id':'AAAAAAAAAAAAAAA',"
                + "'file_size':421608},"
            + "'message_id':514,'from':{'id':98470981,'first_name':'Maksim'}}}");
	    final MemBot bot = new MemBot(
            new AppConfig(App.KEY_NAME).value(),
            new AppConfig(App.KEY_TOKEN).value()
            ) {

            @Override
            void execute(final Command command) {
                assertNotNull("Command is null", command);
                assertTrue(
                    "Wrong command instance",
                    command instanceof UnsupportedMediaCommand
                    );
                succ.set(true);
            }

        };
        bot.onUpdateReceived(update);
        if (!succ.get()) {
            fail("test is not passed");
        }
	}

	private Update update(final String json) {
	    final JSONObject jo = new JSONObject(json);
	    return new Update(jo);
	}
}
