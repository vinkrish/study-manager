package com.app.studymanager.pwdrecovery;

/**
 * Created by Vinay on 11-11-2016.
 */

public interface PwdView {
    void showProgress();

    void hideProgress();

    void setError();

    void setPwdReset();
}
