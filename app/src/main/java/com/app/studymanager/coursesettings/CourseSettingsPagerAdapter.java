package com.app.studymanager.coursesettings;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.studymanager.coursesettings.fragment.difficulty.DifficultyFragment;
import com.app.studymanager.coursesettings.fragment.targetdate.TargetDateFragment;
import com.app.studymanager.models.Course;
import com.app.studymanager.models.CourseSettings;
import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 31-10-2016.
 */

public class CourseSettingsPagerAdapter extends FragmentPagerAdapter {
    private Course course;
    private CourseSettings courseSettings;
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "TARGET DATE", "DIFFICULTY" };

    public CourseSettingsPagerAdapter(FragmentManager fm, Course course,
                                      CourseSettings courseSettings) {
        super(fm);
        this.course = course;
        this.courseSettings = courseSettings;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TargetDateFragment tab1 = TargetDateFragment.newInstance(course ,courseSettings);
                return tab1;
            case 1:
                DifficultyFragment tab2 = DifficultyFragment.newInstance(course, courseSettings);
                return tab2;
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
