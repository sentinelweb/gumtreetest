package co.uk.sentinelweb.gumtree.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.uk.sentinelweb.gumtree.test.model.Advert;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewAdvertismentFragment extends Fragment {

    /**
     * The fragment argument representing the advert id for this fragment.
     */
    private static final String ARG_ADVERT_ID = "advert_id";

    long mAdvertId = -1;
    /** Model object {@link co.uk.sentinelweb.gumtree.test.model.Advert}*/
    Advert mAdvert=null;
    /** The section title view */
    TextView mSectionTitle = null;
    /** The section description view */
    TextView mSectionDescription = null;
    /** The section price view */
    TextView mSectionPrice = null;
    /** The section user view */
    TextView mSectionDatePosted = null;
    /** The section user view */
    TextView mSectionUser = null;
    /** The image counter view */
    TextView mImageCounter = null;
    /** The section title view */
    ViewPager mImagePager = null;

    /** The sharing menu */
    MenuItem mShareMenu = null;
    /** The favs menu */
    MenuItem mFavouriteMenu = null;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ViewAdvertismentFragment newInstance(final Context context, final int advertId) {
        ViewAdvertismentFragment fragment = new ViewAdvertismentFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ADVERT_ID, (long) advertId);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewAdvertismentFragment() {


    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdvertId = getArguments().getLong(ARG_ADVERT_ID,-1l);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_view_advertisment, container, false);
        mSectionTitle = (TextView)rootView.findViewById(R.id.ad_label);
        mSectionDescription = (TextView)rootView.findViewById(R.id.ad_description);
        mSectionPrice = (TextView)rootView.findViewById(R.id.ad_price);
        mSectionUser = (TextView)rootView.findViewById(R.id.ad_user);
        mSectionDatePosted = (TextView)rootView.findViewById(R.id.ad_date);
        mImagePager = (ViewPager)rootView.findViewById(R.id.ad_image_viewpager);
        mImageCounter = (TextView)rootView.findViewById(R.id.ad_image_counter);
        if (mAdvertId>0) {
            mAdvert = ProviderAccessor.getAdvertisment(getActivity(), mAdvertId);
        }

        if (mAdvert!=null) {
            mSectionTitle.setText(mAdvert.getTitle());
            mSectionDescription.setText(mAdvert.getDescription());
            Date datePosted = mAdvert.getDatePosted();
            SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format));
            mSectionDatePosted.setText(dateFormat.format(datePosted));
            //
            final StringBuilder priceFormat = new StringBuilder().append(mAdvert.getPrice()).append("0").append("GBP");
            mSectionPrice.setText(priceFormat.toString());
            mImagePager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return mAdvert.getPhotos().size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup collection, int position) {
                    ImageView v = (ImageView) getLayoutInflater(null).inflate(R.layout.view_advert_image, collection, false);
                    ((ViewPager) collection).addView(v);
                    Picasso.with(getActivity()).load(mAdvert.getPhotos().get(position)).into(v);
                    return v;
                }

                @Override
                public void destroyItem(ViewGroup collection, int position, Object view) {
                    ((ViewPager) collection).removeView((View) view);
                }
            });
            mImagePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    setImageCounter(position);
                    setupSharingMenu(mAdvert.getPhotos().get(position));
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            mImagePager.setCurrentItem(0);
            setImageCounter(0);
        }
        return rootView;
    }

    private void setImageCounter(int position) {
        mImageCounter.setText((position+1)+"/"+mAdvert.getPhotos().size());
    }

    /**
     * Called when the fragment is attached
     * @param activity
     */
    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        final ViewAdvertismentActivity activity1 = (ViewAdvertismentActivity) activity;
        activity1.onSectionAttached(ViewAdvertismentActivity.AD_FRAGMENT_INDEX);

        // load data


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSectionTitle = null;
        mShareMenu = null;
        mFavouriteMenu = null;
    }

    /**
     * Called by system to create the actionbar menu items used in the fragment
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_advertisment, menu);
        mShareMenu =  menu.findItem(R.id.action_share);
        mFavouriteMenu = menu.findItem(R.id.action_favourite);
        mFavouriteMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (!item.isChecked()) {
                    Toast.makeText(getActivity(), "Make favourite", Toast.LENGTH_LONG).show();
                    mFavouriteMenu.setIcon(R.drawable.ic_action_important);
                    mFavouriteMenu.setChecked(true);
                } else {
                    Toast.makeText(getActivity(), "Not a favourite", Toast.LENGTH_LONG).show();
                    mFavouriteMenu.setIcon(R.drawable.ic_action_not_important);
                    mFavouriteMenu.setChecked(false);
                }

                return true;
            }
        });
    }

    public void setupSharingMenu(String url) {
        Uri uri = Uri.parse(url);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");

        //ShareActionProvider shareActionProv = new ShareActionProvider(this);
        ShareActionProvider shareActionProv =         (ShareActionProvider) MenuItemCompat.getActionProvider(mShareMenu);
        shareActionProv.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        shareActionProv.setShareIntent(sendIntent);
    }
}