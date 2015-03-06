package co.uk.sentinelweb.gumtree.test.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import co.uk.sentinelweb.gumtree.test.R;
import co.uk.sentinelweb.gumtree.test.fragment.NavigationDrawerFragment;
import co.uk.sentinelweb.gumtree.test.fragment.PlaceHolderFragment;
import co.uk.sentinelweb.gumtree.test.fragment.ViewAdvertismentFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * For Gumtree testing (this file was generated using the android studio wizard).
 *
 * Generally I would create an Abstract parent Activity which would contain shared functionality needed for all activities. Then subclass that for acitivities.
 *
 * Though many are moving away from having many activities to just having one as the Activity startup is heavy.
 */
public class ViewAdvertismentActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /** We use the toolbar instead of the ActionBar */
    private Toolbar mToolbar;
    /** The current fragment */
    private Fragment mCurrentFragment;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /** Default section index for the advert fragment */
    public static final int AD_FRAGMENT_INDEX = 3;
    public static final int DEFAULT_ADVERT_ID = 1;

    /** Fragment managing the behaviors, interactions and presentation of the navigation drawer. */
    private NavigationDrawerFragment mNavigationDrawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_advertisment);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    /**
     * Callback for the nav drawer to signal an item has been selected
     * We create the fragment and set it into the container
     * @param position
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Fragment fragment = null;
        if (position<AD_FRAGMENT_INDEX) {
            mCurrentFragment = PlaceHolderFragment.newInstance(this, position);
        } else {
            mCurrentFragment = ViewAdvertismentFragment.newInstance(this, DEFAULT_ADVERT_ID);
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, mCurrentFragment)
                .commit();
    }


    /**
     * Callback to be used by fragment after it is attached.
     * @param number The
     */
    public void onSectionAttached(int number) {

    }

    /**
     * Restores the state of the actionbar (after the drawer closes)
     */
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        if (mCurrentFragment instanceof ViewAdvertismentFragment) {
            actionBar.setTitle(null);
        } else {
            actionBar.setTitle(R.string.app_name);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.view_advertisment, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * The standard method implementation method for a menu.
     * @param item the selected item
     * @return true if the item was handled (or call up)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * Gets the toolbar
     * @return
     */
    public Toolbar getToolbar() {
        return mToolbar;
    }


}
