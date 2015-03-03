package co.uk.sentinelweb.gumtree.test.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by robert on 03/03/2015.
 * Skipping jdoc to save time
 */
public class Advert {
    private Long mId;
    private String mTitle;
    private String mDescription;
    private Date mDatePosted;
    private ArrayList<String> mPhotos = new ArrayList<String>();
    private Float mPrice;
    private User mUser;
    private Location mLocation;
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

    public ArrayList<String> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(ArrayList<String> photos) {
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
}
