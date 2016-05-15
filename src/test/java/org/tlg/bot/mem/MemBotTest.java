package org.tlg.bot.mem;

import static org.junit.Assert.fail;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Update;
import org.tlg.bot.mem.commands.Command;
import org.tlg.bot.mem.commands.HelpCommand;
import org.tlg.bot.mem.db.ds.DsHikari;
import org.tlg.bot.mem.db.init.DbTest;

public class MemBotTest {

    private static final Logger log = LoggerFactory
        .getLogger(MemBotTest.class.getName());
    

    @Before
    public void setUp()
        throws SQLException, IOException, URISyntaxException {
        new DbTest(DsHikari.ds()).create();
    }
	@Test
	public void testGetBotUsername() {
	    log.debug("username:{}", new MemBot().getBotUsername());
		MatcherAssert.assertThat(
			"Username is null or emtpy",
			new MemBot().getBotUsername(),
			Matchers.not(Matchers.isEmptyOrNullString())
		);
	}

	@Test
	public void testGetBotToken() {
	    log.debug("token:{}", new MemBot().getBotToken());
	      MatcherAssert.assertThat(
	            "Username is null or emtpy",
	            new MemBot().getBotToken(),
	            Matchers.not(Matchers.isEmptyOrNullString())
	        );
	}
	
	@Test
	public void unrecognizedTextIsHelpCommand() {
	    final AtomicBoolean succ = new AtomicBoolean(false);
	    final MemBot bot = new MemBot() {

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

}
