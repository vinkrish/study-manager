package com.app.studymanager.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.studymanager.R;
import com.app.studymanager.util.SharedPreferenceUtil;

public class ProfileFragment extends Fragment {
    private Bundle bundle;

    private FragmentTabHost mTabHost;

    public ProfileFragment() {}

    public static ProfileFragment newInstance(String param1, String param2) {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = new Bundle();
        bundle.putString("email", SharedPreferenceUtil.getEmail(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("PROFILE").setIndicator("PROFILE"),
                ProfileDetailFragment.class, bundle);
        mTabHost.addTab(mTabHost.newTabSpec("TERMS_OF_USE").setIndicator("TERMS OF USE"),
                TermsOfUseFragment.class, null);

        //mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#06B089"));
        //mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#06B089"));

        return mTabHost;
    }



}
