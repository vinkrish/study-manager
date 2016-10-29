package com.app.studymanager;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Vinay on 26-10-2016.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Asap-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

}
