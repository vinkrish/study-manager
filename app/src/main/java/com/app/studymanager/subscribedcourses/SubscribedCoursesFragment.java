package com.app.studymanager.subscribedcourses;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.app.studymanager.R;
import com.app.studymanager.bottombar.BottomBarActivity;
import com.app.studymanager.courses.CoursesFragment;
import com.app.studymanager.models.Course;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubscribedCoursesFragment extends Fragment implements SubscribedCoursesView {
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.view_list) LinearLayout viewList;
    @BindView(R.id.view_empty) LinearLayout viewEmpty;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int userId;
    private String authToken;

    private SubscribedCoursesPresenter presenter;

    public SubscribedCoursesFragment() {}

    public static SubscribedCoursesFragment newInstance(int param1, String param2) {
        SubscribedCoursesFragment fragment = new SubscribedCoursesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_PARAM1);
            authToken = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscribed_courses, container, false);
        ButterKnife.bind(this, view);
        presenter = new SubscribedCoursesPresenterImpl(this, new SubscribedCoursesInteractorImpl());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgess() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setSubscribedCourses(List<Course> subscribedCourses) {
        if(subscribedCourses != null && subscribedCourses.isEmpty()) {
            viewEmpty.setVisibility(View.VISIBLE);
        } else {
            viewList.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new SubscribedCoursesAdapter(getActivity(), subscribedCourses));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(userId, authToken);
    }

}
