package com.app.studymanager.subscribedcourses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.bottombar.BottomBarActivity;
import com.app.studymanager.courseupdate.CourseUpdateActivity;
import com.app.studymanager.models.Book;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.SubscribedCourses;
import com.app.studymanager.util.AdapterCallback;
import com.app.studymanager.util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscribedCoursesFragment extends Fragment
        implements SubscribedCoursesView, AdapterCallback {

    @BindView(R.id.username_empty) TextView usernameEmpty;
    @BindView(R.id.username) TextView username;
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
    private Credentials credentials;

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
        credentials = SharedPreferenceUtil.getUserToken(getActivity());
        name = SharedPreferenceUtil.getUsername(getActivity());
        if(name.equals("")){
            name = (SharedPreferenceUtil.getEmail(getActivity()).split("@"))[0];
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscribed_courses, container, false);
        ButterKnife.bind(this, view);
        usernameEmpty.setText(name);
        presenter = new SubscribedCoursesPresenterImpl(this, new SubscribedCoursesInteractorImpl());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        adapterCallback = this;
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
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
            username.setText(name);
            recyclerView.setAdapter(new SubscribedCoursesAdapter(getActivity(), subscribedCourses.getCourses(), adapterCallback));
        }
    }

    @Override
    public void setSaved() {
        onResume();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(userId, authToken);
    }

    @Override
    public void onMoreCallback(long id, String date) {
        Course course = new Course();
        course.setId(id);
        SharedPreferenceUtil.saveCourse(getActivity(), course);
        startActivity(new Intent(getActivity(), CourseUpdateActivity.class));
    }

    @Override
    public void onSaveCallback(long id, Book book) {
        presenter.onSave(credentials, id, book);
    }
}
