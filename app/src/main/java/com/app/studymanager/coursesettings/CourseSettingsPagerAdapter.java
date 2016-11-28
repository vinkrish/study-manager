package com.app.studymanager.coursesettings;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.studymanager.coursesettings.fragment.difficulty.DifficultyFragment;
import com.app.studymanager.coursesettings.fragment.targetdate.TargetDateFragment;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.CourseSettings;
import com.app.studymanager.models.Credentials;

import java.util.HashMap;

/**
 * Created by Vinay on 31-10-2016.
 */

class CourseSettingsPagerAdapter extends FragmentPagerAdapter {
    Fragment targetDate;
    private Course course;
    private CourseSettings courseSettings;
    private final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "SET TARGET DATE", "SET READING SPEED" };

    CourseSettingsPagerAdapter(FragmentManager fm, Course course,
                                      CourseSettings courseSettings) {
        super(fm);
        this.course = course;
        this.courseSettings = courseSettings;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if(targetDate == null){
                    targetDate = TargetDateFragment.newInstance(course, courseSettings);
                }
                return targetDate;
            case 1:
                return DifficultyFragment.newInstance(course, courseSettings);
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
