package com.app.studymanager.courses;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.app.studymanager.R;
import com.app.studymanager.coursedetails.CourseDetailsActivity;
import com.app.studymanager.models.Course;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoursesFragment extends Fragment implements CoursesView {
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int userId;
    private String authToken;

    private CoursesPresenter presenter;

    public CoursesFragment() {}

    public static CoursesFragment newInstance(int param1, String param2) {
        CoursesFragment fragment = new CoursesFragment();
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
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        ButterKnife.bind(this, view);
        presenter = new CoursesPresenterImpl(this, new CoursesInteractorImpl());
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
    public void setCourses(List<Course> courses) {
        recyclerView.setAdapter(new CoursesAdapter(courses, new CoursesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course item) {
                navigateToCourseDetails(item);
            }
        }));
    }

    @Override
    public void navigateToCourseDetails(Course course) {
        Intent intent = new Intent(getActivity(), CourseDetailsActivity.class);
        intent.putExtra("courseId", course.getId());
        intent.putExtra("subscribed", course.getSubscribed());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(userId, authToken);
    }
}
