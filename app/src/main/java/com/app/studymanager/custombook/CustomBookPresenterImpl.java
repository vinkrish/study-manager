package com.app.studymanager.custombook;

import com.app.studymanager.models.Book;
import com.app.studymanager.models.Credentials;

/**
 * Created by Vinay on 08-11-2016.
 */

public class CustomBookPresenterImpl implements CustomBookPresenter,
        CustomBookInteractor.OnFinishedListener {

    private CustomBookView customBookView;
    private CustomBookInteractor interactor;

    public CustomBookPresenterImpl(CustomBookView customBookView, CustomBookInteractor interactor) {
        this.customBookView = customBookView;
        this.interactor = interactor;
    }

    @Override
    public void onAdd(Credentials credentials, long courseId, Book book) {
        if(customBookView != null) {
            customBookView.showProgress();
            interactor.addCustomBook(credentials, courseId, book, this);
        }
    }

    @Override
    public void onDestroy() {
        customBookView = null;
    }

    @Override
    public void onAdded() {
        if(customBookView != null) {
            customBookView.hideProgess();
            customBookView.setAdd();
        }
    }

    @Override
    public void onError() {
        if(customBookView != null) {
            customBookView.hideProgess();
            customBookView.showError();
        }
    }
}
