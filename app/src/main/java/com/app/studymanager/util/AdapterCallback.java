package com.app.studymanager.util;

import com.app.studymanager.models.Book;

/**
 * Created by Vinay on 03-11-2016.
 */

public interface AdapterCallback {
    void onMoreCallback(long id, String date);
    void onSaveCallback(long id, Book book);
}
