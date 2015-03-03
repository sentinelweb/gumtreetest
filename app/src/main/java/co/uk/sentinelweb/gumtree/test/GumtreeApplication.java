package co.uk.sentinelweb.gumtree.test;

import android.app.Application;

/**
 * Created by robert on 03/03/2015.
 */
public class GumtreeApplication extends Application {

    /**
     * Gets the title string to use in actionBar
     * @param pos
     * @return
     */
    public String getSectionName(int pos) {
        switch(pos)
        {
            case 0:
                return getString(R.string.title_home);
            case 1:
                return getString(R.string.title_search);
            case 2:
                return getString(R.string.title_favourites);
            case 3:
                return getString(R.string.title_view_ad);

        }
        return getString(R.string.app_name);
    }

}
