package com.app.studymanager.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Vinay on 30-10-2016.
 */

public class DisplayUtil {
    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int px2dp(Context context, int px){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px - 0.5f / scale);
    }

    public static float convertPixelsToDp(Context context, float px){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dp = px / ((float)displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int dp2px(Context context, int dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static float convertDpToPixel(Context context, float dp){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float px = dp * ((float)displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}
