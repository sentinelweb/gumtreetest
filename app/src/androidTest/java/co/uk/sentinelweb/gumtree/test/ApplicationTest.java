package co.uk.sentinelweb.gumtree.test;

import android.test.ApplicationTestCase;

import co.uk.sentinelweb.gumtree.lib.model.Advert;
import co.uk.sentinelweb.gumtree.lib.model.User;
import co.uk.sentinelweb.gumtree.test.provider.ProviderAccessor;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<GumtreeApplication> {

    public ApplicationTest() {
        super(GumtreeApplication.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createApplication();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testContentProvider() throws Exception {
        Advert advertisment = ProviderAccessor.getAdvertisment(getApplication(), 1);
        // check advert is not null
        assertNotNull(advertisment);
        // check fields are not null
        assertNotNull(advertisment.getId());
        assertNotNull(advertisment.getTitle());
        assertNotNull(advertisment.getDescription());
        assertNotNull(advertisment.getDatePosted());
        assertNotNull(advertisment.getPrice());
        // there are photos
        assertTrue(advertisment.getPhotos().size() > 0);
        // check User
        User user = advertisment.getUser();
        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getPhone());
    }

    public void testGetTitle() throws Exception {
        String s = ((GumtreeApplication)getApplication()).getSectionName(-1);
        assertNotNull(s);
        assertTrue(s.equals("Gumtree Test"));
    }
}