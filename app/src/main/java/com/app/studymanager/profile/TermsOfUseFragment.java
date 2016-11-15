package com.app.studymanager.profile;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.studymanager.R;
import com.roughike.bottombar.BottomBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsOfUseFragment extends Fragment {
    @BindView(R.id.moreInfo) TextView moreInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms_of_use, container, false);
        ButterKnife.bind(this, view);
        moreInfo.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }

}
