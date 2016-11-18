package com.app.studymanager.courseupdate;

import com.app.studymanager.models.Course;

/**
 * Created by Vinay on 03-11-2016.
 */

public interface CourseUpdateView {
    void showProgress();

    void hideProgess();

    void setCourse(Course course);

    void setUpdate();

    void whileUnscribed();

    void setDeleted();

    void cantDelete();

    void showError();
}
