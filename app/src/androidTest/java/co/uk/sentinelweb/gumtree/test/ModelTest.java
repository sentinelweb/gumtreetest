package co.uk.sentinelweb.gumtree.test;

import junit.framework.TestCase;

import co.uk.sentinelweb.gumtree.test.model.Advert;
import co.uk.sentinelweb.gumtree.test.model.User;

/**
 * Created by robert on 03/03/2015.
 */
public class ModelTest extends TestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    public void testModelConstruct() throws Exception {
        Advert a = new Advert();
        User u = new User();
    }
}
