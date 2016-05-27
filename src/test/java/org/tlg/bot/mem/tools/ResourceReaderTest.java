package org.tlg.bot.mem.tools;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Optional;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class ResourceReaderTest {

    @Test
    public void test() throws URISyntaxException {
        final String name = "/hikari.properties";
        final Optional<String> read = ResourceReader.read(name);
        final File expFile = Paths.get(ResourceReaderTest.class.getResource(name).toURI()).toFile();
        
        MatcherAssert.assertThat(
            "Readed is not same as existed",
            read.get().length(),
            Matchers.is((int) expFile.length())
            );
    }

}
