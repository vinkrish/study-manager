package com.app.studymanager.custombook;

import com.app.studymanager.models.Book;
import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 07-11-2016.
 */

public interface CustomBookInteractor {
    interface OnFinishedListener {
        void onAdded();

        void onError();
    }

    void addCustomBook(Credentials credentials, long courseId,
                       Book book, OnFinishedListener listener);
}
