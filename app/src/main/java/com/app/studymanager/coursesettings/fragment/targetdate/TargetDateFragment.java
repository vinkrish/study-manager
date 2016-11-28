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
import com.app.studymanager.util.DateUtil;
import com.app.studymanager.util.SharedPreferenceUtil;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TargetDateFragment extends Fragment {
    @BindView(R.id.target_date_tv) TextView targetDateStatus;
    @BindView(R.id.description_tv) TextView description;
    @BindView(R.id.target_tv) TextView target;
    @BindView(R.id.min_target_date) TextView validTargetDate;

    private View view;
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
        view = inflater.inflate(R.layout.fragment_target_date, container, false);
        ButterKnife.bind(this, view);

        if(course.getDescription() == null || course.getDescription().equals("")){
            description.setText("Not Available");
        } else description.setText(course.getDescription());

        target.setText(DateUtil.getDisplayTargetDate(courseSettings.getTargetDate()));
        if(courseSettings.getDefaultView()!=null && courseSettings.getDefaultView().equals("TARGET_DATE")){
            targetDateStatus.setText(getString(R.string.target_date_on));
        } else {
            targetDateStatus.setText(getString(R.string.target_date_off));
        }

        if(SharedPreferenceUtil.getTargetDateVisibility(getActivity())) {
            isValidTargetDate(true, SharedPreferenceUtil.getTargetDate(getActivity()));
        }

        return view;
    }

    public void isValidTargetDate(boolean visibile, String date){
        if(visibile){
            validTargetDate.setVisibility(View.VISIBLE);
            validTargetDate.setText(String.format(Locale.ENGLISH,
                    "Cannot complete the course by %s as per your settings", date));
        } else {
            if (validTargetDate != null && validTargetDate.getVisibility() == View.VISIBLE) {
                validTargetDate.setVisibility(View.GONE);
            }
        }
    }

}
