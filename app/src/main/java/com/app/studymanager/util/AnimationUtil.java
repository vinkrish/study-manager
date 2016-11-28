package com.app.studymanager.util;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/**
 * Created by Vinay on 28-11-2016.
 */

public class AnimationUtil {

    public static void alphaTranslate(View view, Context context) {
        view.setVisibility(View.VISIBLE);
        AnimationSet animationSet = new AnimationSet(context , null);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, view.getHeight(), 0);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(700);
        view.startAnimation(animationSet);
    }

}
