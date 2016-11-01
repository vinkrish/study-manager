package com.app.studymanager.coursesettings.fragment.difficulty;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.studymanager.R;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.CourseSettings;
import com.app.studymanager.models.Credentials;

public class DifficultyFragment extends Fragment {
    private Course course;
    private CourseSettings courseSettings;

    public DifficultyFragment() {}

    public static DifficultyFragment newInstance(Course course, CourseSettings courseSettings) {
        DifficultyFragment fragment = new DifficultyFragment();
        Bundle args = new Bundle();
        if(courseSettings != null){
            args.putSerializable("course", course);
            args.putSerializable("settings", courseSettings);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        courseSettings = (CourseSettings)args.getSerializable("settings");
        course = (Course)args.getSerializable("course");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_difficulty, container, false);
    }

}
