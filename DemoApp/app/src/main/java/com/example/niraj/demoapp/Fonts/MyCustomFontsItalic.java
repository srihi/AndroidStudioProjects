package com.example.niraj.demoapp.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Niraj on 18-01-2017.
 */

public class MyCustomFontsItalic extends TextView {
    public MyCustomFontsItalic(Context context) {
        super(context);
        init(context);
    }

    public MyCustomFontsItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyCustomFontsItalic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Fonts/Ubuntu-Italic.ttf"));

    }
}
