/**
 * 
 */
package org.tlg.bot.mem;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.tlg.bot.mem.db.ds.DsHikari;
import org.vmk.db.Db;

/**
 * App starter.
 * @author "Maksim Vakhnik"
 *
 */
public class App {

    private static final Logger log = LoggerFactory
        .getLogger(App.class.getName());
    public static final String KEY_TOKEN = "membot.token";
    public static final String KEY_NAME = "membot.name";
    private static final String CMD_DROP_TABLE = "--drop-tables";
    private static final String CMD_WEB_CONSOLE = "--console";
    
    private static final String SQL_DROP_TABLES = "/sql/drop-tables.sql";
    private static final String SQL_CREATE_TABLE = "/sql/create-tables.sql";
    
    
    
    public static void main(final String... args)
        throws SQLException, IOException, URISyntaxException {
      
        if (App.hasArgument(CMD_DROP_TABLE, args)) {
            App.dropTables();
        }
        initDb();
        if (App.hasArgument(CMD_WEB_CONSOLE, args)) {
            Server.startWebServer(DsHikari.ds().dataSource().getConnection());  
            return;
        }
        final TelegramBotsApi api = new TelegramBotsApi();
        try {
            log.debug("try register a bot...");
            
            api.registerBot(
                new MemBot(
                    new AppConfig(App.KEY_NAME).value(),
                    new AppConfig(App.KEY_TOKEN).value()
                    )
                );
            log.debug("bot registered");
        } catch (final TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static boolean hasArgument(final String cmd, final String... args) {
        return args.length > 0 && Arrays.asList(args).contains(cmd);
    }

    private static void dropTables()
        throws SQLException, IOException, URISyntaxException {
        new Db(DsHikari.ds(), Paths
            .get(App.class.getResource(App.SQL_DROP_TABLES).toURI()).toFile())
                .exec();
    }

    private static void initDb()
        throws SQLException, IOException, URISyntaxException {
        
        new Db(
            DsHikari.ds(),
            App.class.getResourceAsStream(App.SQL_CREATE_TABLE)
        ).exec();
    }


}
