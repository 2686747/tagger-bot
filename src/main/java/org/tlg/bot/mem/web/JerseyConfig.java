package org.tlg.bot.mem.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.tlg.bot.mem.web.rest.RsTagsEditor;
import org.tlg.bot.mem.web.rest.RsTagsMainPage;

/**
 * 
 * @author Maksim Vakhnik
 *
 */
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        super();
        register(new FreemarkerMvcFeature());
        register(RsTagsMainPage.class);
        register(RsTagsEditor.class);
    }

}
