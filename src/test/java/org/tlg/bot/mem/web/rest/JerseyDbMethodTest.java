/**
 * 
 */
package org.tlg.bot.mem.web.rest;

import javax.ws.rs.core.Application;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.test.JerseyTestNg;
import org.glassfish.jersey.test.TestProperties;
import org.testng.annotations.BeforeMethod;
import org.tlg.bot.mem.db.init.DbTest;
import org.tlg.bot.mem.web.JerseyConfig;
import org.tlg.bot.mem.db.ds.Ds;
import helper.db.TestDs;

/**
 * Abstract class for testing with {@link TestDs()}. new database will be created
 * for each testing method
 * 
 * @author Maksim Vakhnik
 *
 */
public class JerseyDbMethodTest extends JerseyTestNg.ContainerPerMethodTest {
    private Ds ds;

    @Override
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();
        new DbTest(this.getDs()).create();
    }
    @Override
    protected Application configure() {
        this.forceSet(TestProperties.CONTAINER_PORT, "0");
        return new JerseyConfig().register(new AbstractBinder() {

            @Override
            protected void configure() {
                JerseyDbMethodTest.this.ds = new TestDs();
                bind(JerseyDbMethodTest.this.getDs()).to(Ds.class);
            }

        });
    }

    public Ds getDs() {
        return this.ds;
    }
}
