package co.uk.sentinelweb.gumtree.lib.model;


import java.util.ArrayList;
import java.util.Date;

/**
 * Created by robert on 03/03/2015.
 * Skipping jdoc to save time
 */
public class Advert {
    /**
     * Item Location (since we cant use Android android.location.Location as this is a plain java lib).
     */
    public class ItemLocation {
        private double mLat;
        private double mLon;

        public double getLat() {
            return mLat;
        }

        public void setLat(double lat) {
            this.mLat = lat;
        }

        public double getLon() {
            return mLon;
        }

        public void setLon(double lon) {
            this.mLon = lon;
        }
        /**
         * gets a geo: URL for use in launch maps intent;
         * @return geo:latitude,longitude or n
         *
         */
        public String getGeoUrl(){
            return new StringBuilder().append("geo:").append(mLocation.getLat()).append(",").append(mLocation.getLon()).toString();
        }
    }

    /**
     * Photo data (URL and color attributes)
     */
    public class PhotoData {
        String mUrl;
        int mHighlightColor = -1;

        public PhotoData(String url) {
            mUrl = url;
        }

        public String getUrl() {
            return mUrl;
        }

        public void setUrl(String url) {
            mUrl = url;
        }

        public int getHighlightColor() {
            return mHighlightColor;
        }

        public void setHighlightColor(int highlightColor) {
            mHighlightColor = highlightColor;
        }
    }
    private Long mId;
    private String mTitle;
    private String mDescription;
    private Date mDatePosted;
    private ArrayList<PhotoData> mPhotos = new ArrayList<PhotoData>();
    private Float mPrice;
    private User mUser;
    private ItemLocation mLocation;
    private String mPlace;
    private String mUrl;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public ArrayList<PhotoData> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(ArrayList<PhotoData> photos) {
        mPhotos = photos;
    }

    public Float getPrice() {
        return mPrice;
    }

    public void setPrice(Float price) {
        mPrice = price;
    }

    public Date getDatePosted() {
        return mDatePosted;
    }

    public void setDatePosted(Date datePosted) {
        mDatePosted = datePosted;
    }

    public ItemLocation getLocation() {
        return mLocation;
    }

    public void setLocation(ItemLocation location) {
        mLocation = location;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }


}
