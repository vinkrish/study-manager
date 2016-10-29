package com.app.studymanager.subscribedcourses;

import com.app.studymanager.models.Course;

import java.util.List;

/**
 * Created by Vinay on 27-10-2016.
 */

public class SubscribedCoursesPresenterImpl implements
        SubscribedCoursesPresenter,
        SubscribedCoursesInteractor.OnFinishedListener {

    private SubscribedCoursesView subscribedCoursesView;
    private SubscribedCoursesInteractor subscribedCoursesInteractor;

    public SubscribedCoursesPresenterImpl(SubscribedCoursesView subscribedCoursesView,
                                          SubscribedCoursesInteractor subscribedCoursesInteractor){
        this.subscribedCoursesView = subscribedCoursesView;
        this.subscribedCoursesInteractor = subscribedCoursesInteractor;
    }


    @Override
    public void onResume(int userId, String authToken) {
        if(subscribedCoursesView != null) {
            subscribedCoursesView.showProgress();
            subscribedCoursesInteractor.fetchSubscribedCourses(userId, authToken, this);
        }
    }

    @Override
    public void onCourseSelected(long courseId) {

    }

    @Override
    public void onDestroy() {
        subscribedCoursesView = null;
    }

    @Override
    public void onFinished(List<Course> courses) {
        if(subscribedCoursesView != null) {
            subscribedCoursesView.setSubscribedCourses(courses);
            subscribedCoursesView.hideProgess();
        }
    }
}
