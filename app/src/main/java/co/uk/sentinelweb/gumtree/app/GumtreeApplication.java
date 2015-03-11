package co.uk.sentinelweb.gumtree.app;

import android.app.Application;

import co.uk.sentinelweb.gumtree.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by robert on 03/03/2015.
 */
public class GumtreeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    /**
     * Gets the title string to use in actionBar.
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
