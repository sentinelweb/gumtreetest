package co.uk.sentinelweb.gumtree.lib.test;

import junit.framework.TestCase;

import java.util.Date;

import co.uk.sentinelweb.gumtree.lib.model.Advert;
import co.uk.sentinelweb.gumtree.lib.model.User;

/**
 * A pure Java test
 * Created by robert on 03/03/2015.
 */
public class ModelTest extends TestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test constructors of models
     * @throws Exception
     */
    public void testModelConstruct() throws Exception {
        Advert a = new Advert();
        User u = new User();
        assertNotNull(a);
        assertNotNull(u);
    }

    /**
     * Fills in an {@link co.uk.sentinelweb.gumtree.lib.model.Advert} object and check that everything comes back as expected.
     * @throws Exception
     */
    public void testAdvertFill() throws Exception {
        final String testString = "testString";
        final Date datePosted = new Date();
        final Float testFloat = 20f;
        final Double testDouble = 20.0;
        final Long testLong = System.currentTimeMillis();

        //fill in advert
        final Advert a = new Advert();
        a.setId(testLong);
        a.setDatePosted(datePosted);
        a.setTitle(testString);
        a.setDescription(testString);
        a.setUrl(testString);
        a.setPlace(testString);
        a.setPrice(testFloat);

        // fill in a location
        final Advert.ItemLocation testLocation= a.new ItemLocation();
        testLocation.setLat(testDouble);
        testLocation.setLon(testDouble);
        a.setLocation(testLocation);

        // fill in a photo
        assertNotNull(a.getPhotos());
        final Advert.PhotoData testPhoto = a.new PhotoData(testString);
        a.getPhotos().add(testPhoto);

        // User fields are tested in {@see testUserFill}
        final User u = new User();
        a.setUser(u);

        // retrieval checks
        assertTrue(testLong == a.getId());
        assertTrue(testString.equals(a.getDescription()));
        assertTrue(testString.equals(a.getTitle()));
        assertTrue(testString.equals(a.getUrl()));
        assertTrue(testString.equals(a.getPlace()));
        assertTrue(testFloat.equals(a.getPrice()));

        assertNotNull(a.getDatePosted());
        assertTrue(datePosted.getTime()==a.getDatePosted().getTime());

        assertNotNull(a.getLocation());
        assertTrue(testDouble.equals(a.getLocation().getLat()));
        assertTrue(testDouble.equals(a.getLocation().getLon()));

        assertNotNull(a.getPhotos());
        assertTrue(a.getPhotos().size()==1);
        assertNotNull(a.getPhotos().get(0));
        assertTrue(testString.equals(a.getPhotos().get(0).getUrl()));

        assertNotNull(a.getUser());

    }

    /**
    * Fills in an {@link co.uk.sentinelweb.gumtree.lib.model.User} object and check that everything comes back as expected.
    * @throws Exception
    */
    public void testUserFill() throws Exception {
        final String testString = "testString";
        final Long testLong = System.currentTimeMillis();

        final User u = new User();
        u.setId(testLong);
        u.setEmail(testString);
        u.setImageUrl(testString);
        u.setName(testString);
        u.setTitle(testString);
        u.setPhone(testString);

        // retrieval checks
        assertTrue(testLong == u.getId());
        assertTrue(testString.equals(u.getName()));
        assertTrue(testString.equals(u.getEmail()));
        assertTrue(testString.equals(u.getImageUrl()));
        assertTrue(testString.equals(u.getPhone()));
        assertTrue(testString.equals(u.getTitle()));

    }


}
