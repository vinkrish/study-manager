package com.app.studymanager.profile;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.studymanager.R;
import com.roughike.bottombar.BottomBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsOfUseFragment extends Fragment {

    public TermsOfUseFragment() {}

    public static TermsOfUseFragment newInstance() {
        TermsOfUseFragment fragment = new TermsOfUseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms_of_use, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private boolean isViewVisible2(View view) {
        Rect scrollBounds = new Rect();
        //scrollView.getHitRect(scrollBounds);
        if (view.getLocalVisibleRect(scrollBounds)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isViewVisible1(View view) {
        Rect scrollBounds = new Rect();
        //scrollView.getDrawingRect(scrollBounds);

        float top = view.getY();
        float bottom = top + view.getHeight();

        if (scrollBounds.top < top && scrollBounds.bottom > bottom) {
            return true;
        } else {
            return false;
        }
    }

}
