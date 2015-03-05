package co.uk.sentinelweb.gumtree.test;

import co.uk.sentinelweb.gumtree.test.activity.ViewAdvertismentActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.melnykov.fab.FloatingActionButton;

public class ViewAdsActivityTest extends ActivityInstrumentationTestCase2<ViewAdvertismentActivity> {
    ViewAdvertismentActivity mViewAdsActivity = null;
    public ViewAdsActivityTest() {
		super(ViewAdvertismentActivity.class);
	}

	protected void setUp() throws Exception {
        mViewAdsActivity = getActivity();
	}

	public void testToolbar() {
        Toolbar toolbar = mViewAdsActivity.getToolbar();
        assertNotNull(toolbar);
	}

    Boolean result = null;

    public void testImagePager() {
        final ViewPager vp = (ViewPager)mViewAdsActivity.findViewById(R.id.ad_image_viewpager);
        result = null;
        mViewAdsActivity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    assertTrue(vp.getCurrentItem() == 0);
                    vp.setCurrentItem(1);
                    assertTrue(vp.getCurrentItem() == 1);
                    vp.setCurrentItem(2);
                    assertTrue(vp.getCurrentItem() == 3);
                    result=true;
                } catch (AssertionError e) {
                    result=false;
                }
            }}
        );
        while (result == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertTrue(result);
    }

    public void testPhoneButton() {
        final FloatingActionButton phoneButton = (FloatingActionButton) mViewAdsActivity.findViewById(R.id.ad_phone_button);

        mViewAdsActivity.runOnUiThread(new Runnable() {
            public void run() {
                phoneButton.performClick();
            }
        });
    }

    public void testEmailButton() {
        Toolbar toolbar = mViewAdsActivity.getToolbar();
        assertNotNull(toolbar);
        final FloatingActionButton emailButton = (FloatingActionButton) mViewAdsActivity.findViewById(R.id.ad_email_button);

        mViewAdsActivity.runOnUiThread(new Runnable() {
            public void run() {
                emailButton.performClick();
            }
        });
    }

	protected void tearDown() throws Exception {

	}

}
