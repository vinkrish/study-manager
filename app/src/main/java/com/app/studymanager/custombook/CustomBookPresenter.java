package com.app.studymanager.custombook;

import com.app.studymanager.models.Book;
import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 07-11-2016.
 */

public interface CustomBookPresenter {

    void onAdd(Credentials credentials, long courseId, Book book);

    void onDestroy();
}
