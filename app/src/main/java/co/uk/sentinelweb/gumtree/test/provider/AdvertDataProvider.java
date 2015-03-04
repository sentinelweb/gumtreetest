package co.uk.sentinelweb.gumtree.test.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.uk.sentinelweb.gumtree.test.Statics;

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
    public static final String Q_AD_PLACE = "place";
    public static final String Q_AD_URL = "url";

    // photo
    public static final String Q_IMG_INDEX = "index";
    public static final String Q_IMG_URL = "url";

    // user
    public static final String Q_USER_NAME = "name";
    public static final String Q_USER_PHONE = "phone";
    public static final String Q_USER_EMAIL = "email";
    public static final String Q_USER_IMG = "image";



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
            final MatrixCursor mc = new MatrixCursor(new String[]{Q_ID, Q_AD_TITLE, Q_AD_DESCRIPTION, Q_AD_DATE, Q_AD_PRICE, Q_AD_USER_ID, Q_AD_LAT, Q_AD_LONG, Q_AD_PLACE, Q_AD_URL});
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
                    MOCK_TITLES[id],
                    id+" "+MOCK_DESCRIPTION,
                    formattedDate,
                    id * 100,
                    0,
                    theLocation.getLatitude(),
                    theLocation.getLongitude(),
                    "Hackney, London",
                    "http://www.gumtree.com/p/macs/macbook-2008-black/1102895424"
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
            final MatrixCursor mc = new MatrixCursor( new String[]{ Q_ID, Q_USER_NAME, Q_USER_PHONE, Q_USER_EMAIL,Q_USER_IMG} );
            mc.addRow( new Object[] { id, "Joe Bloggs", "0789623406"+(id%10), "user"+id+"@gmail.com","http://lh4.googleusercontent.com/-DpMuLNbBl8k/AAAAAAAAAAI/AAAAAAAAB34/Qz1x6ZXegwA/s80-c/photo.jpg" } );
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
    private static final String MOCK_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris placerat enim nulla, at dignissim augue venenatis sit amet. Nulla a lorem vel nisl dignissim ultricies at vulputate mi. Nulla nec nibh id diam aliquet dapibus. Pellentesque nisi libero, gravida nec lacus ac, laoreet rutrum mi. Vivamus ullamcorper purus ut sagittis pretium. Curabitur blandit felis ut diam viverra, nec luctus nibh vestibulum. Donec ut tortor sit amet elit eleifend rhoncus. Fusce laoreet vehicula tempus.\n" +
            "\n" +
            "Mauris eu erat consectetur, ornare arcu at, finibus lacus. Phasellus egestas sem lectus, eget vestibulum nunc commodo eget. Etiam vitae massa in purus commodo imperdiet. Proin diam tortor, condimentum ut erat id, mollis ultrices eros. Nam eget turpis euismod sapien lacinia ultricies vel commodo sapien. Sed non interdum sapien. Curabitur ultricies purus a metus fermentum dapibus vel a enim. Donec at mi interdum, pulvinar metus at, pretium lacus. Donec ac faucibus orci, scelerisque pretium felis. Donec aliquet risus ac dui tempus consequat. Maecenas accumsan libero eu lectus efficitur, sagittis egestas arcu tempor. Mauris eleifend placerat vulputate. Vivamus molestie ipsum ac auctor tincidunt. Suspendisse tristique imperdiet tincidunt. Vestibulum lectus nisl, tincidunt et elit in, tincidunt vulputate metus. Duis fermentum tortor ut sapien feugiat malesuada.\n" +
            "\n" +
            "Suspendisse eget ultricies sem. Pellentesque dapibus diam nunc. Nullam ac lacus sed mauris consequat porta nec quis leo. Maecenas imperdiet sit amet metus sed maximus. Curabitur elementum fringilla pharetra. Sed eleifend odio maximus, lobortis velit nec, tincidunt lectus. Nunc eu accumsan diam.\n" +
            "\n" +
            "Integer pharetra non felis non placerat. Quisque enim lacus, tempor non fermentum ultrices, semper eget nisl. Nullam sem elit, interdum a mauris accumsan, interdum elementum mi. Cras et dui odio. Nulla ac consequat dui. Vestibulum quis metus convallis, maximus neque quis, efficitur odio. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean dignissim metus justo, vel accumsan nulla blandit vitae.\n" +
            "\n" +
            "Vestibulum condimentum augue sodales eros fermentum aliquet. Etiam commodo vel lacus a facilisis. Fusce accumsan semper libero vel ornare. Aliquam viverra iaculis tellus a maximus. Donec vehicula ac lacus at efficitur. Suspendisse accumsan semper lacus, condimentum vehicula velit pellentesque sed. Nulla euismod interdum iaculis. Aliquam vulputate, est sed laoreet lacinia, orci arcu elementum magna, non scelerisque eros lectus pharetra dui. Ut sed arcu vel nulla dignissim ultrices a ultricies ligula. Aenean sodales ac tortor eu condimentum. Pellentesque euismod rhoncus molestie. Praesent elementum facilisis erat, id volutpat nunc sollicitudin sit amet.";

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
    final static String[] MOCK_TITLES = new String[]{
            "Leather Massage chair",
            "Macbook Pro 2011 Hardly used"
    };
    //Mock photos taken from some random gumtree ads.
    final static String[][] MOCK_PHOTOS = new String[][]{
            new String[]{
                    "http://i.ebayimg.com/00/s/ODAwWDYwMA==/z/fG0AAOSwEeFU9HlR/$_86.JPG",
                    "http://i.ebayimg.com/00/s/ODAwWDYwMA==/z/dzUAAOSwBLlU9HlR/$_86.JPG",
                    "http://i.ebayimg.com/00/s/ODAwWDYwMA==/z/qIIAAOSwPYZU9HlR/$_86.JPG"
            },
            new String[]{
                    "http://i.ebayimg.com/00/s/MTAyNFg4MDA=/z/GzwAAOSwrklU9RDL/$_86.JPG",
                    "http://i.ebayimg.com/00/s/NTc2WDEwMjQ=/z/IWsAAOSwZjJU9RDA/$_86.JPG",
                    "http://i.ebayimg.com/00/s/NTc2WDEwMjQ=/z/pn8AAOSwEeFU9RDG/$_86.JPG",
                    "http://i.ebayimg.com/00/s/Njc3WDEwMjQ=/z/ZQIAAOSwBLlU9RC4/$_86.JPG",
                    "http://i.ebayimg.com/00/s/NTc2WDEwMjQ=/z/KesAAOSweW5U9RCx/$_86.JPG"

            }
    };
}
