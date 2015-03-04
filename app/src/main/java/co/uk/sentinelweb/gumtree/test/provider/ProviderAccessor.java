package co.uk.sentinelweb.gumtree.test.provider;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import co.uk.sentinelweb.gumtree.test.Statics;
import co.uk.sentinelweb.gumtree.test.model.Advert;
import co.uk.sentinelweb.gumtree.test.model.User;
import co.uk.sentinelweb.gumtree.test.provider.AdvertDataProvider;

/**
 * Created by robert on 03/03/2015.
 */
public class ProviderAccessor {

    private static final String CONTENT_URI_ADVERT = "content://"+ AdvertDataProvider.CONTENT_AUTHORITY+"/"+AdvertDataProvider.PATH_ADVERT_DATA;
    private static final String CONTENT_URI_ADVERT_IMG = "content://"+AdvertDataProvider.CONTENT_AUTHORITY+"/"+AdvertDataProvider.PATH_ADVERT_PHOTOS;
    private static final String CONTENT_URI_USER = "content://"+AdvertDataProvider.CONTENT_AUTHORITY+"/"+AdvertDataProvider.PATH_USER;

    /**
     * Gets the advert data.
     * @param c
     * @param id
     * @return
     */
    public static Advert getAdvertisment(final Context c, final long id) {
        Cursor cursor = c.getContentResolver().query(Uri.parse(CONTENT_URI_ADVERT), null, null, new String[]{Long.toString(id)}, null);
        if (cursor!=null) {
            Advert advert = new Advert();
            final long userId;
            try {
                cursor.moveToFirst();
                //get the advert data
                advert.setId(cursor.getLong(cursor.getColumnIndex(AdvertDataProvider.Q_ID)));
                advert.setTitle(cursor.getString(cursor.getColumnIndex(AdvertDataProvider.Q_AD_TITLE)));
                advert.setDescription(cursor.getString(cursor.getColumnIndex(AdvertDataProvider.Q_AD_DESCRIPTION)));
                advert.setPrice(cursor.getFloat(cursor.getColumnIndex(AdvertDataProvider.Q_AD_PRICE)));
                advert.setPlace(cursor.getString(cursor.getColumnIndex(AdvertDataProvider.Q_AD_PLACE)));
                advert.setUrl(cursor.getString(cursor.getColumnIndex(AdvertDataProvider.Q_AD_URL)));

                // get the posted date
                SimpleDateFormat dateparser = AdvertDataProvider.DATE_FORMATTER;
                try {
                    String rawDateString = cursor.getString(cursor.getColumnIndex(AdvertDataProvider.Q_AD_DATE));
                    java.util.Date dateParsed = dateparser.parse(rawDateString);
                    advert.setDatePosted(dateParsed);
                    Log.d(Statics.LOG_TAG, "Got date ;" + dateParsed + " for :" + rawDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // get the location
                long latitude = cursor.getLong(cursor.getColumnIndex(AdvertDataProvider.Q_AD_LAT));
                long longitude = cursor.getLong(cursor.getColumnIndex(AdvertDataProvider.Q_AD_LONG));
                Location whereIsIt = new Location("");
                whereIsIt.setLatitude(latitude);
                whereIsIt.setLongitude(longitude);
                advert.setLocation(whereIsIt);
                userId = cursor.getLong(cursor.getColumnIndex(AdvertDataProvider.Q_AD_USER_ID));
            } finally {
                // ensure we close the cursor
                cursor.close();
            }

            Cursor photocursor = c.getContentResolver().query(Uri.parse(CONTENT_URI_ADVERT_IMG), null, null, new String[]{Long.toString(id)}, null);
            if (photocursor!=null) {
                try {
                    while (photocursor.moveToNext()) {
                            advert.getPhotos().add(advert.new PhotoData(photocursor.getString(photocursor.getColumnIndex(AdvertDataProvider.Q_IMG_URL))));
                    }
                } finally {
                    // ensure we close the cursor
                    photocursor.close();
                }
            }
            Cursor usercursor = c.getContentResolver().query(Uri.parse(CONTENT_URI_USER), null, null, new String[]{Long.toString(userId)}, null);
            if (usercursor!=null) {
                User user = new User();
                try {
                    usercursor.moveToFirst();
                    user.setId(usercursor.getLong(usercursor.getColumnIndex(AdvertDataProvider.Q_ID)));
                    user.setName(usercursor.getString(usercursor.getColumnIndex(AdvertDataProvider.Q_USER_NAME)));
                    user.setEmail(usercursor.getString(usercursor.getColumnIndex(AdvertDataProvider.Q_USER_EMAIL)));
                    user.setPhone(usercursor.getString(usercursor.getColumnIndex(AdvertDataProvider.Q_USER_PHONE)));
                    user.setImageUrl(usercursor.getString(usercursor.getColumnIndex(AdvertDataProvider.Q_USER_IMG)));
                    advert.setUser(user);
                } finally {
                    // ensure we close the cursor
                    usercursor.close();
                }
            }
            return advert;
        }
        else return null;

    }
}
