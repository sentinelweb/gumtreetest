package co.uk.sentinelweb.gumtree.test.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.uk.sentinelweb.gumtree.test.images.CircleTransform;
import co.uk.sentinelweb.gumtree.test.model.User;
import co.uk.sentinelweb.gumtree.test.provider.ProviderAccessor;
import co.uk.sentinelweb.gumtree.test.R;
import co.uk.sentinelweb.gumtree.test.activity.ViewAdvertismentActivity;
import co.uk.sentinelweb.gumtree.test.images.PalletteTransformation;
import co.uk.sentinelweb.gumtree.test.model.Advert;
import co.uk.sentinelweb.gumtree.test.util.SendIntent;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewAdvertismentFragment extends Fragment {

    /**
     * The fragment argument representing the advert id for this fragment.
     */
    private static final String ARG_ADVERT_ID = "advert_id";

    public static final int ANIM_DURATION_MILLIS = 500;

    //// Data fields
    /** The ad id */
    long mAdvertId = -1;
    /** Model object {@link co.uk.sentinelweb.gumtree.test.model.Advert}*/
    Advert mAdvert=null;
    /**
    /** current HighlightColor */
    private Integer mCurrentHighlightColor = null;


    //// UI Element fields
    /** The  title view */
    TextView mSectionTitle = null;
    /** The section title view */
    ViewGroup mSectionTitleFrame = null;
    /** The  description view */
    TextView mSectionDescription = null;
    /** The  price view */
    TextView mSectionPrice = null;
    /** The place text */
    TextView mSectionPlace = null;
    /** The  user view */
    TextView mSectionDatePosted = null;
    /** The  user view */
    TextView mSectionUser = null;
    /** The  user image */
    ImageView mSectionUserImage = null;
    /** The  user phone */
    TextView mSectionUserPhone = null;
    /** The  user email */
    TextView mSectionUserEmail = null;
    /** The image counter view */
    TextView mImageCounter = null;
    /** The  image pager */
    ViewPager mImagePager = null;
    /** The outer scroll view */
    ScrollView mScrollView = null;
    /** Contact Main FAB*/
    FloatingActionButton mContactButton = null;
    /** Phone FAB*/
    FloatingActionButton mPhoneButton = null;
    /** Email FAB*/
    FloatingActionButton mEmailButton = null;
    /** Text FAB*/
    FloatingActionButton mTextButton = null;

    //// MenuItem fields
    /** The sharing menu */
    MenuItem mShareMenu = null;
    /** The favs menu */
    MenuItem mFavouriteMenu = null;

    /** General purpose Handler for UI stuff*/
    Handler mHandler = null;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ViewAdvertismentFragment newInstance(final Context context, final int advertId) {
        final ViewAdvertismentFragment fragment = new ViewAdvertismentFragment();
        final Bundle args = new Bundle();
        args.putLong(ARG_ADVERT_ID, (long) advertId);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewAdvertismentFragment() {

    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        mAdvertId = getArguments().getLong(ARG_ADVERT_ID,-1l);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_view_advertisment, container, false);
        mSectionTitle = (TextView) rootView.findViewById(R.id.ad_title);
        mSectionTitleFrame = (ViewGroup) rootView.findViewById(R.id.ad_label_ctnr);
        mSectionDescription = (TextView) rootView.findViewById(R.id.ad_description);
        mSectionPrice = (TextView) rootView.findViewById(R.id.ad_price);
        mSectionUser = (TextView) rootView.findViewById(R.id.ad_user);
        mSectionPlace = (TextView) rootView.findViewById(R.id.ad_place);
        mSectionUserPhone = (TextView) rootView.findViewById(R.id.ad_user_phone);
        mSectionUserEmail = (TextView) rootView.findViewById(R.id.ad_user_email);
        mSectionUserImage = (ImageView) rootView.findViewById(R.id.ad_user_img);
        mSectionDatePosted = (TextView) rootView.findViewById(R.id.ad_date);
        mImagePager = (ViewPager) rootView.findViewById(R.id.ad_image_viewpager);
        mImageCounter = (TextView) rootView.findViewById(R.id.ad_image_counter);
        mScrollView = (ScrollView) rootView.findViewById(R.id.ad_scroll_outer);

        mContactButton = (FloatingActionButton) rootView.findViewById(R.id.ad_contact_button);
        mPhoneButton = (FloatingActionButton) rootView.findViewById(R.id.ad_phone_button);
        mTextButton = (FloatingActionButton) rootView.findViewById(R.id.ad_text_button);
        mEmailButton = (FloatingActionButton) rootView.findViewById(R.id.ad_email_button);

        mContactButton.setOnClickListener(mContactClickListener);
        mPhoneButton.setOnClickListener(mPhoneClickListener);
        mTextButton.setOnClickListener(mSmsClickListener);
        mEmailButton.setOnClickListener(mEmailClickListener);

        if (mAdvertId>0) {
            mAdvert = ProviderAccessor.getAdvertisment(getActivity(), mAdvertId);
        }

        if (mAdvert!=null) {
            mSectionTitle.setText(mAdvert.getTitle());
            mSectionDescription.setText(mAdvert.getDescription());
            Date datePosted = mAdvert.getDatePosted();
            SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format));
            mSectionDatePosted.setText(dateFormat.format(datePosted));
            final StringBuilder priceFormat = new StringBuilder().append("£").append(mAdvert.getPrice()).append("0");
            mSectionPrice.setText(priceFormat.toString());
            mImagePager.setAdapter(mPagerAdapter);
            mImagePager.setOnPageChangeListener(mOnPageChangeListener);
            mScrollView.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener);
            mImagePager.setCurrentItem(0);
            setImageCounter(0);
            mSectionPlace.setText(mAdvert.getPlace());
            User user = mAdvert.getUser();
            if (user !=null) {
                mSectionUser.setText(user.getName());
                mSectionUserEmail.setText(user.getEmail());
                mSectionUserPhone.setText(user.getPhone());
                Picasso.with(getActivity()).load(user.getImageUrl()).transform(new CircleTransform()).into(mSectionUserImage);
            }
        }


        return rootView;
    }

    /**
     * Gets the highlight color from the extracted Palette
     * @param photoData
     */
    private void setHighlightColor(final Advert.PhotoData photoData) {
        if (mSectionTitle !=null && photoData!=null) {
            final int mNewColour = photoData.getHighlightColor();
            final int startColor = mCurrentHighlightColor != null ? mCurrentHighlightColor : Color.TRANSPARENT;
            final ColorDrawable[] color = {new ColorDrawable(startColor), new ColorDrawable(mNewColour)};
            final TransitionDrawable trans = new TransitionDrawable(color);
            setViewBackground(mSectionTitleFrame, trans);

            trans.startTransition(ANIM_DURATION_MILLIS);
            mHandler.postDelayed( new Runnable() {
                @Override
                public void run() {
                    mCurrentHighlightColor=mNewColour;
                }
            }, ANIM_DURATION_MILLIS);
        }
    }

    /**
     * This will work also on old devices. The latest API says you have to use setBackground instead.
     * which is really not great IMHO.
     * Not setting the drawable wipes out the padding - thanks google. have to manually set it again.
     * @param trans
     */
    private void setViewBackground(final View view, final Drawable trans) {
        if (Build.VERSION.SDK_INT>=16) {
            view.setBackground(trans);
        } else {
            view.setBackgroundDrawable(trans);
        }
//        int padding = (int)getResources().getDimension(R.dimen.activity_horizontal_margin);
//        view.setPadding(padding,padding,padding,padding);
    }

    /**
     * Sets the text for the image counter
     * @param position the image position
     */
    private void setImageCounter(final int position) {
        mImageCounter.setText((position+1)+" / "+mAdvert.getPhotos().size());
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

    /**
     * Clear all member {@link android.view.View}'s here
     * You can setDrawable callback here too the famous memory leaks only happens it the activity so it not essential.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSectionTitle = null;
        mSectionDescription = null;
        mSectionPrice = null;
        mSectionDatePosted = null;
        mSectionUser = null;
        mShareMenu = null;
        mFavouriteMenu = null;
        mSectionTitleFrame = null;
        mSectionPlace = null;
        mSectionUserPhone = null;
        mSectionUserEmail = null;
        mSectionUserImage = null;
        mSectionDatePosted = null;
        mImagePager = null;
        mImageCounter = null;
        mScrollView = null;
    }

    /**
     * Called by system to create the actionbar menu items used in the fragment
     * @param menu the menu
     * @param inflater menu inflater
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
                    Toast.makeText(getActivity(), getString(R.string.toast_make_favourite), Toast.LENGTH_SHORT).show();
                    mFavouriteMenu.setIcon(R.drawable.ic_action_important);
                    mFavouriteMenu.setChecked(true);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.toast_unmake_favourite), Toast.LENGTH_SHORT).show();
                    mFavouriteMenu.setIcon(R.drawable.ic_action_not_important);
                    mFavouriteMenu.setChecked(false);
                }
                return true;
            }
        });

    }

    /**
     * Sets up the sharing menu for use in the action bar - shares the current image URl - but it the real app it would of course be the web link
     * @param url the URL to share
     */
    public void setupSharingMenu(final String url) {
        final Uri uri = Uri.parse(url);
        final Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");

        final ShareActionProvider shareActionProv =   (ShareActionProvider) MenuItemCompat.getActionProvider(mShareMenu);
        // Sometime it's null on a rotation change - think there is a bit of a timing issue between onCreateOptionsMenu/ViewPager change listener to sort here.
        if (shareActionProv!=null) {
            shareActionProv.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
            shareActionProv.setShareIntent(sendIntent);
        }
    }

    // Listeners ///////////////////////////////////////////////////
    /** Scroll listener for the page scroll container
     * This listener detects when the page has scrolled past the title bar and set the toolbar title and color - and reverese this back when the title is scrolled down again
     * There background color change should be animated.
     */
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
        boolean titleWasBelowActionBar = true;
        @Override
        public void onScrollChanged() {
            final int scrollX = mScrollView.getScrollX();
            final int scrollY = mScrollView.getScrollY();
            // check for title bar going above/below action bar and modify toolbar accordingly
            if (scrollY>mSectionTitleFrame.getTop() && titleWasBelowActionBar) {
                final Toolbar toolbar = ((ViewAdvertismentActivity) getActivity()).getToolbar();
                if (toolbar!=null) {
                    toolbar.setTitle(mSectionTitle.getText());
                    if (mCurrentHighlightColor!=null) {
                        toolbar.setBackgroundDrawable(new ColorDrawable(mCurrentHighlightColor));
                    }
                }
                mSectionTitle.setVisibility(View.INVISIBLE);
                titleWasBelowActionBar=false;
            } else if (scrollY<mSectionTitleFrame.getTop() && !titleWasBelowActionBar) {
                final Toolbar toolbar = ((ViewAdvertismentActivity) getActivity()).getToolbar();
                if (toolbar!=null) {
                    toolbar.setTitle(null);
                    toolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_black_to_tsp));
                }
                mSectionTitle.setVisibility(View.VISIBLE);
                titleWasBelowActionBar=true;
            }
            // This is a cheap parallax effect, as the image is at the top of the screen already.
            mImagePager.setTop((int)(scrollY*0.4f));
            // check to hide the minor FAB buttons
            // something still wrong with this the buttons should hide on the scroll start but dont see to hide until touch off
            if (mEmailButton.getVisibility()==View.VISIBLE){
                //animateMinorButtonsHide();
//                fadeOut(mEmailButton);
//                fadeOut(mTextButton);
//                fadeOut(mPhoneButton);
                mEmailButton.setVisibility(View.GONE);
                mTextButton.setVisibility(View.GONE);
                mPhoneButton.setVisibility(View.GONE);
            }
            // Log.d(Statics.LOG_TAG, "scrollY:"+scrollY+" titletop:"+mSectionTitleFrame.getTop());
        }
    };

    /** The click listener for the contact FAB button */
    private View.OnClickListener mContactClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            if (mEmailButton.getVisibility()==View.GONE) {
                animateMinorButtonsShow();
            } else if (mEmailButton.getVisibility()==View.VISIBLE){
                animateMinorButtonsHide();
            }
        }
    };

    /** The click listener for the phone FAB button */
    private View.OnClickListener mPhoneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            SendIntent.launchDialIntent(getActivity(), mAdvert.getUser().getPhone());
        }
    };
    /** The click listener for the sms FAB button */
    private View.OnClickListener mSmsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            SendIntent.launchSmsIntent(getActivity(), mAdvert.getUser().getPhone(), getString(R.string.sms_body, mAdvert.getUrl()));
        }
    };
    /** The click listener for the email FAB button */
    private View.OnClickListener mEmailClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            SendIntent.launchEmailIntent(getActivity(),  getString(R.string.email_subject, mAdvert.getTitle()), getString(R.string.email_body, mAdvert.getTitle(), mAdvert.getUrl()), null);
        }
    };
    /** Page change listener for the images viewpager */
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(final int position) {
            setImageCounter(position);
            Advert.PhotoData photoData = mAdvert.getPhotos().get(position);
            setupSharingMenu(photoData.getUrl());
            setHighlightColor(photoData);
        }

        @Override
        public void onPageScrollStateChanged(final int state) {

        }
    };

    // Adapters ///////////////////////////////////////////////////
    /** The adapter for images viewpager */
    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        PalletteTransformation mPalletteTrans = new PalletteTransformation();
        boolean firstTime = true;
        @Override
        public int getCount() {
            return mAdvert.getPhotos().size();
        }

        @Override
        public boolean isViewFromObject(final View view, final Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(final ViewGroup collection, final int position) {
            ImageView v = (ImageView) getLayoutInflater(null).inflate(R.layout.view_advert_image, collection, false);
            ((ViewPager) collection).addView(v);
            final Advert.PhotoData photoData = mAdvert.getPhotos().get(position);
            Picasso.with(getActivity()).load(photoData.getUrl()).transform(mPalletteTrans).into(v, new Callback() {
                @Override
                public void onSuccess() {
                    // Stuff to do on image loaded
                    // We generate the color palette for the image
                    Palette palette = mPalletteTrans.getGeneratedPalette();
                    if (palette!=null && photoData.getHighlightColor()==-1) {
                        photoData.setHighlightColor(palette.getDarkMutedColor(Color.BLACK));
                        if (firstTime) {
                            setHighlightColor(photoData);
                            setupSharingMenu(photoData.getUrl());
                            firstTime=false;
                        }
                    }
                }

                @Override
                public void onError() {

                }
            });
            return v;
        }

        @Override
        public void destroyItem(final ViewGroup collection, final int position, final Object view) {
            ((ViewPager) collection).removeView((View) view);
        }
    };


    //////////////// Animations //////////////////////////////////////////////////////////////////
    private static final int FAB_ANIMATE_TIME= 500;
    /**
     * The animation for showing the minor buttons
     */
    private void animateMinorButtonsShow() {
        animateFABButton(mEmailButton, true);
        animateFABButton(mTextButton, true);
        animateFABButton(mPhoneButton, true);
    }

    /**
     * The animation for hiding the minor buttons
     */
    private void animateMinorButtonsHide() {
        animateFABButton(mEmailButton, false);
        animateFABButton(mTextButton, false);
        animateFABButton(mPhoneButton, false);
    }

    /**
     * Animate a button (fade in/out).
     * Probably i would do translate and fade here if i had more time.
     * @param v the view to aminate
     * @param in show = true
     */
    private void animateFABButton(final View v, final boolean in){
        final ObjectAnimator mAnimator = ObjectAnimator.ofFloat(v,"Alpha",in?0:1,in?1:0);
        mAnimator.setDuration(FAB_ANIMATE_TIME)
                 .addListener(new AnimatorListenerAdapter() {
                                  @Override
                                  public void onAnimationStart(Animator animator) {
                                      if (in && v.getVisibility() != View.VISIBLE) {
                                          v.setVisibility(View.VISIBLE);
                                      }
                                  }

                                  @Override
                                  public void onAnimationEnd(Animator animator) {
                                      if (!in && v.getVisibility() != View.GONE) {
                                          v.setVisibility(View.GONE);
                                      }
                                  }
                              }
                 );
        mAnimator.start();
    }

    /**
     * This uses view animation for the fade out (seem to be a problem using object animation)
     * NOT USED - had to just set visibility instead :(
     * @param v the view to animate
     */
    private void fadeOut(final View v) {
        if (v.getVisibility()!=View.GONE) {
            Animation scrollHideOutAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            scrollHideOutAnim.setDuration(FAB_ANIMATE_TIME);
            scrollHideOutAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (v != null) v.setVisibility(View.GONE);
                }
            });
            v.startAnimation(scrollHideOutAnim);
        }
    }

}