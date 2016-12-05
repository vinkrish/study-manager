package com.app.studymanager.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Vinay on 04-12-2016.
 */

public class VerticalButton extends Button {
    String s = "SAVE";

    public VerticalButton(Context context) {
        super(context);
    }

    public VerticalButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {

        for(int i=0;i<text.length();i++)
        {
            if(s == null) s = "";
            s = s + String.valueOf(text.charAt(i))+ "\n";
        }
        super.setText(s, type);

    }
}
