package com.app.studymanager.coursesettings.fragment.targetdate;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.CourseSettings;
import com.app.studymanager.util.DatePickerFragment;
import com.app.studymanager.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TargetDateFragment extends Fragment {
    @BindView(R.id.title) TextView title;
    @BindView(R.id.description_tv) TextView description;
    @BindView(R.id.target_tv) TextView target;
    @BindView(R.id.min_target_date) TextView validTargetDate;
    @BindView(R.id.change_btn) Button changeDateBtn;

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

        if(course.getDescription() == null || course.getDescription().equals("")){
            description.setText("Not Available");
        } else description.setText(course.getDescription());

        target.setText(DateUtil.getDisplayTargetDate(courseSettings.getTargetDate()));

        changeDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDate();
            }
        });

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

    public void changeDate() {
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(courseSettings.getTargetDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("year",year);
        bundle.putInt("month",month);
        bundle.putInt("day", day);
        newFragment.setCallBack(ondate);
        newFragment.setArguments(bundle);
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            Calendar cal = Calendar.getInstance();
            cal.set(year, monthOfYear, dayOfMonth);
            Date date = cal.getTime();

            Calendar nearestTargetCalendar = Calendar.getInstance();
            Date nearestTargetDate = new Date();
            try {
                nearestTargetDate = dateFormat.parse(courseSettings.getNearestTargetDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            nearestTargetCalendar.setTime(nearestTargetDate);

            if(nearestTargetCalendar.get(Calendar.YEAR) > cal.get(Calendar.YEAR)) {
                isValidTargetDate(true, displayFormat.format(date));
            } else if(nearestTargetCalendar.get(Calendar.MONTH) > cal.get(Calendar.MONTH) &&
                    nearestTargetCalendar.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
                isValidTargetDate(true, displayFormat.format(date));
            } else if(nearestTargetCalendar.get(Calendar.DAY_OF_MONTH) > cal.get(Calendar.DAY_OF_MONTH) &&
                    nearestTargetCalendar.get(Calendar.MONTH) >= cal.get(Calendar.MONTH) &&
                    nearestTargetCalendar.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
                isValidTargetDate(true, displayFormat.format(date));
            } else {
                isValidTargetDate(false, "");
                courseSettings.setTargetDate(dateFormat.format(date));
                target.setText(DateUtil.getDisplayTargetDate(courseSettings.getTargetDate()));
            }
        }
    };

}
