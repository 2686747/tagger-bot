package org.tlg.bot.mem.web;

import org.glassfish.hk2.api.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tlg.bot.mem.db.ds.DsHikari;
import org.vmk.db.ds.Ds;

public class DsFactory implements Factory<Ds>{
    private static final Logger log = LoggerFactory
        .getLogger(DsFactory.class.getName());
    
    @Override
    public void dispose(final Ds ds) {
        // do nothing
        log.debug("displose:{}", ds);
    }

    @Override
    public Ds provide() {
        return DsHikari.ds();
    }

}
