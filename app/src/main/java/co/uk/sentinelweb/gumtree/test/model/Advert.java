package co.uk.sentinelweb.gumtree.test.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by robert on 03/03/2015.
 * Skipping jdoc to save time
 */
public class Advert {
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
    private Location mLocation;
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

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
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
