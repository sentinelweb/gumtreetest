package co.uk.sentinelweb.gumtree.test;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provider for the advert data
 * Uses the mock data declared at the bottom of the file
 */
public class AdvertDataProvider extends ContentProvider {
    /** Content provider authority string */
    public static final String CONTENT_AUTHORITY="co.uk.sentinelweb.gumtree.test.provider.advert_data";
    /** Query ad data path */
    public static final String PATH_ADVERT_DATA = "advert_data";
    /** Query ad photos path */
    public static final String PATH_ADVERT_PHOTOS = "advert_photos";
    /** Query ad user path */
    public static final String PATH_USER = "user";

    /** Date format */
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    /** Date formatter */
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);
    /* (not going to doc these to save time,but i would normally) */
    public static final String Q_ID = "id";

    // advert data
    public static final String Q_AD_TITLE = "title";
    public static final String Q_AD_DESCRIPTION = "description";
    public static final String Q_AD_DATE = "date";
    public static final String Q_AD_PRICE = "price";
    public static final String Q_AD_USER_ID = "user_id";
    public static final String Q_AD_LAT = "lat";
    public static final String Q_AD_LONG = "long";

    // photo
    public static final String Q_IMG_INDEX = "index";
    public static final String Q_IMG_URL = "url";

    // user
    public static final String Q_USER_NAME = "name";
    public static final String Q_USER_PHONE = "phone";
    public static final String Q_USER_EMAIL = "email";



    public AdvertDataProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    /**
     * The query method fetches from the data store
     *
     * One might use the projection argument to do a inner join on a database.
     *
     * Possibly some ORM would be better (ORMLite, SugarORM, GreenDAO).
     *
     * @param uri data type to provide
     * @param projection not used
     * @param selection not used
     * @param selectionArgs used to pass advert id
     * @param sortOrder not used
     * @return {@link android.database.Cursor} Mock Data
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
//        // TODO: Implement this to handle query requests from clients.
//        throw new UnsupportedOperationException("Not yet implemented");
        int id = Integer.valueOf(selectionArgs[0]);

        if (uri.getPath().indexOf(PATH_ADVERT_DATA)>-1) {
            final MatrixCursor mc = new MatrixCursor(new String[]{Q_ID, Q_AD_TITLE, Q_AD_DESCRIPTION, Q_AD_DATE, Q_AD_PRICE, Q_AD_USER_ID, Q_AD_LAT, Q_AD_LONG});
            Location theLocation = null;
            try {
                theLocation = MOCK_COORDINATES[id];
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                theLocation = new Location("");
                // set to HQ
                theLocation.setLatitude(51.515538);
                theLocation.setLongitude(-0.108769);
            }
            Date theDate = new Date();
            String formattedDate = DATE_FORMATTER.format(theDate);
            Log.d(Statics.LOG_TAG, "Got date formatted;"+formattedDate+" for :"+theDate);
            mc.addRow(new Object[]{
                    id,
                    "The item title " + id,
                    "Description for :" + id,
                    formattedDate,
                    id * 100,
                    1,
                    theLocation.getLatitude(),
                    theLocation.getLongitude()
            });
            return mc;
        } else if (uri.getPath().indexOf(PATH_ADVERT_PHOTOS)>-1) {
            final MatrixCursor mc = new MatrixCursor(new String[]{ Q_ID, Q_IMG_INDEX,Q_IMG_URL});
            // looping backwards is faster - but doesn't look a nice maybe.
            String[] photoArray = MOCK_PHOTOS[(id%2)];
            for (int i= photoArray.length-1; i>=0; i--) {
                mc.addRow(new Object[] {id, i, photoArray[i]});
            }
            return mc;
        } else if (uri.getPath().indexOf(PATH_USER)>-1) {
            final MatrixCursor mc = new MatrixCursor( new String[]{ Q_ID, Q_USER_NAME, Q_USER_PHONE, Q_USER_EMAIL} );
            mc.addRow( new Object[] { id, "User "+id, "0789623406"+(id%10), "user"+id+"@gmail.com" } );
            return mc;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    ///////////////////////////// MOCK DATA //////////////////////////////////////

    /* Mock data for provider */
    final static Location[] MOCK_COORDINATES = new Location[2];
    static {
        Location one = new Location("");
        one.setLatitude(51.529887);
        one.setLongitude(-0.086419);
        MOCK_COORDINATES[0]=one;
        Location two = new Location("");
        two.setLatitude(51.529887);
        two.setLongitude(-0.086419);
        MOCK_COORDINATES[1]=two;
    }

    //Mock photos taken from some random gumtree ads.
    final static String[][] MOCK_PHOTOS = new String[][]{
            new String[]{
                    "http://i.ebayimg.com/00/s/ODAwWDYwMA==/z/fG0AAOSwEeFU9HlR/$_86.JPG",
                    "http://i.ebayimg.com/00/s/ODAwWDYwMA==/z/dzUAAOSwBLlU9HlR/$_86.JPG",
                    "http://i.ebayimg.com/00/s/ODAwWDYwMA==/z/qIIAAOSwPYZU9HlR/$_86.JPG"
            },
            new String[]{
                    "http://i.ebayimg.com/00/s/NTc2WDEwMjQ=/z/KesAAOSweW5U9RCx/$_86.JPG",
                    "http://i.ebayimg.com/00/s/Njc3WDEwMjQ=/z/ZQIAAOSwBLlU9RC4/$_86.JPG",
                    "http://i.ebayimg.com/00/s/NTc2WDEwMjQ=/z/IWsAAOSwZjJU9RDA/$_86.JPG",
                    "http://i.ebayimg.com/00/s/NTc2WDEwMjQ=/z/pn8AAOSwEeFU9RDG/$_86.JPG",
                    "http://i.ebayimg.com/00/s/MTAyNFg4MDA=/z/GzwAAOSwrklU9RDL/$_86.JPG"
            }
    };
}
