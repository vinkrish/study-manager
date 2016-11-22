package com.app.studymanager.subscribedcourses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.bottombar.BottomBarActivity;
import com.app.studymanager.courseupdate.CourseUpdateActivity;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.SubscribedCourses;
import com.app.studymanager.util.AdapterCallback;
import com.app.studymanager.util.SharedPreferenceUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscribedCoursesFragment extends Fragment
        implements SubscribedCoursesView, AdapterCallback {

    @BindView(R.id.welcome_msg) TextView welcomeMsg;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.view_list) LinearLayout viewList;
    @BindView(R.id.view_empty) LinearLayout viewEmpty;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int userId;
    private String name;
    private String authToken;
    private AdapterCallback adapterCallback;

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
        name = (SharedPreferenceUtil.getEmail(getActivity()).split("@"))[0];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscribed_courses, container, false);
        ButterKnife.bind(this, view);
        presenter = new SubscribedCoursesPresenterImpl(this, new SubscribedCoursesInteractorImpl());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterCallback = this;
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
    public void showError() {
        ((BottomBarActivity)getActivity()).showAPIError(getString(R.string.error_msg));
    }

    @Override
    public void showAPIError(String message) {
        ((BottomBarActivity)getActivity()).showAPIError(message);
    }

    @Override
    public void setSubscribedCourses(SubscribedCourses subscribedCourses) {
        if(subscribedCourses.getCourses() != null && subscribedCourses.getCourses().isEmpty()) {
            viewEmpty.setVisibility(View.VISIBLE);
        } else {
            viewList.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new SubscribedCoursesAdapter(getActivity(), subscribedCourses.getCourses(), adapterCallback));
            welcomeMsg.setText(String.format(Locale.ENGLISH,
                    "Welcome %s, You had last updated your progress in StudyManager on %s.",
                    name, getDate(subscribedCourses.getLastUpdatedDate())));
        }
    }

    private String getDate(String dateString) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayFormat.format(date);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(userId, authToken);
    }

    @Override
    public void onMethodCallback(long id, String date) {
        Course course = new Course();
        course.setId(id);
        SharedPreferenceUtil.saveCourse(getActivity(), course);
        startActivity(new Intent(getActivity(), CourseUpdateActivity.class));
    }
}
