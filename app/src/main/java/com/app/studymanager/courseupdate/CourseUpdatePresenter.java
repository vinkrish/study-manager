package com.app.studymanager.courseupdate;

import com.app.studymanager.models.Book;
import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 03-11-2016.
 */

public interface CourseUpdatePresenter {

    void onResume(Credentials credentials, long courseId);

    void onUpdate(Credentials credentials, long courseId, Book book);

    void unsubscribeCourse(Credentials credentials, long courseId);

    void deleteBook(Credentials credentials, long courseId, long bookId);

    void onDestroy();
}
