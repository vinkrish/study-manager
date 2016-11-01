package com.app.studymanager.coursesettings.fragment.targetdate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.CourseSettings;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TargetDateFragment extends Fragment {
    @BindView(R.id.title_tv) TextView title;
    @BindView(R.id.description_tv) TextView description;
    @BindView(R.id.target_tv) TextView target;

    private Course course;
    private CourseSettings courseSettings;

    public TargetDateFragment() {}

    public static TargetDateFragment newInstance(Course course, CourseSettings courseSettings) {
        TargetDateFragment fragment = new TargetDateFragment();
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
        View view = inflater.inflate(R.layout.fragment_target_date, container, false);
        ButterKnife.bind(this, view);
        title.setText(course.getTitle());
        description.setText(course.getDescription());
        target.setText(getTargetDate(courseSettings.getTargetDate()));
        return view;
    }

    private String getTargetDate(String dateString) {
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

}
