package com.app.studymanager.subscribedcourses;

/*
 * Created by Vinay on 27-10-2016.
 */

import com.app.studymanager.models.Book;
import com.app.studymanager.models.Credentials;

public interface SubscribedCoursesPresenter {
    void onResume(int userId, String authToken);

    void onSave(Credentials credentials, long courseId, Book book);

    void onDestroy();
}
