package co.uk.sentinelweb.gumtree.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceHolderFragment extends Fragment {
    /**
     * The fragment argument representing the section name for this fragment.
     */
    private static final String ARG_SECTION_NAME = "section_name";

    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /** The section title view */
    TextView mSectionTitle = null;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceHolderFragment newInstance(final Context context, final int sectionNumber) {
        PlaceHolderFragment fragment = new PlaceHolderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        final GumtreeApplication applicationContext = (GumtreeApplication) context.getApplicationContext();
        final String sectionName = applicationContext.getSectionName(sectionNumber);
        args.putString(ARG_SECTION_NAME, sectionName);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceHolderFragment() {

    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_placeholder, container, false);
        mSectionTitle = (TextView)rootView.findViewById(R.id.section_label);
        mSectionTitle.setText(getArguments().getString(ARG_SECTION_NAME));
        return rootView;
    }

    /**
     * Called when the fragment is attached
     * @param activity
     */
    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        final ViewAdvertismentActivity activity1 = (ViewAdvertismentActivity) activity;
        activity1.onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
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
    }


}