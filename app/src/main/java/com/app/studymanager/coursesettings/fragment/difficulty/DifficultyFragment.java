package com.app.studymanager.coursesettings.fragment.difficulty;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.app.studymanager.R;
import com.app.studymanager.coursesettings.CourseSettingsActivity;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.CourseSettings;
import com.app.studymanager.models.Credentials;
import com.app.studymanager.models.WeeklyHours;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DifficultyFragment extends Fragment {
    @BindView(R.id.title_tv) TextView title;
    @BindView(R.id.description_tv) TextView description;
    @BindView(R.id.radio_easy) RadioButton radioEasy;
    @BindView(R.id.radio_moderate) RadioButton radioModerate;
    @BindView(R.id.radio_aggressive) RadioButton radioAggressive;
    @BindView(R.id.mon) EditText mon;
    @BindView(R.id.tue) EditText tue;
    @BindView(R.id.wed) EditText wed;
    @BindView(R.id.thu) EditText thu;
    @BindView(R.id.fri) EditText fri;
    @BindView(R.id.sat) EditText sat;
    @BindView(R.id.sun) EditText sun;
    @BindView(R.id.max_hours) TextView maxHours;
    @BindView(R.id.save_btn) Button save;

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
        View view = inflater.inflate(R.layout.fragment_difficulty, container, false);
        ButterKnife.bind(this, view);
        title.setText(course.getTitle());
        description.setText(course.getDescription());
        setInputHours(courseSettings.getWeeklyHours());
        setPrepTime(courseSettings.getProficiency());
        setHoursWatcher();
        //plannedDate.setText(getTargetDate(courseSettings.getTargetDate()));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeeklyHours weeklyHours = new WeeklyHours();
                int monHours = Integer.parseInt(mon.getText().toString());
                int tueHours = Integer.parseInt(tue.getText().toString());
                int wedHours = Integer.parseInt(wed.getText().toString());
                int thuHours = Integer.parseInt(thu.getText().toString());
                int friHours = Integer.parseInt(fri.getText().toString());
                int satHours = Integer.parseInt(sat.getText().toString());
                int sunHours = Integer.parseInt(sun.getText().toString());

                weeklyHours.setMonday(monHours);
                weeklyHours.setTuesday(tueHours);
                weeklyHours.setWednesday(wedHours);
                weeklyHours.setThursday(thuHours);
                weeklyHours.setFriday(friHours);
                weeklyHours.setSaturday(satHours);
                weeklyHours.setSunday(sunHours);

                int hours = monHours + tueHours + wedHours + thuHours + friHours + satHours + sunHours;
                if (hours >= 1) {
                    ((CourseSettingsActivity)getActivity()).saveDifficultySettings(weeklyHours);
                } else {
                    ((CourseSettingsActivity)getActivity()).showInputError();
                }

            }
        });

        return view;
    }

    private void setHoursWatcher(){
        mon.addTextChangedListener(new HoursTextWatcher());
        tue.addTextChangedListener(new HoursTextWatcher());
        wed.addTextChangedListener(new HoursTextWatcher());
        thu.addTextChangedListener(new HoursTextWatcher());
        fri.addTextChangedListener(new HoursTextWatcher());
        sat.addTextChangedListener(new HoursTextWatcher());
        sun.addTextChangedListener(new HoursTextWatcher());
    }

    private void setInputHours(WeeklyHours week) {
        mon.setText(String.format("%S", week.getMonday()));
        tue.setText(String.format("%S", week.getTuesday()));
        wed.setText(String.format("%S", week.getWednesday()));
        thu.setText(String.format("%S", week.getThursday()));
        fri.setText(String.format("%S", week.getFriday()));
        sat.setText(String.format("%S", week.getSaturday()));
        sun.setText(String.format("%S", week.getSunday()));
    }

    private void setPrepTime(String preparation){
        switch(preparation){
            case "EASY":
                radioEasy.setChecked(true);
                break;
            case "MODERATE":
                radioModerate.setChecked(true);
                break;
            case "DIFFICULT":
                radioAggressive.setChecked(true);
                break;
            default:
                break;
        }
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

    class HoursTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(!editable.toString().equals("") && Integer.parseInt(editable.toString()) > 16){
                maxHours.setVisibility(View.VISIBLE);
                editable.replace(0, editable.length(), 16+"");
            } else {
                //maxHours.setVisibility(View.GONE);
            }
        }
    }

}
