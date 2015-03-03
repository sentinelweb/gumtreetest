package co.uk.sentinelweb.gumtree.test.model;

/**
 * Created by robert on 03/03/2015.
 * Skipping jdoc to save time
 */
public class User {
    private Long mId;
    private String mTitle;
    private String mName;
    private String mEmail;
    private String mPhone;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }
}
