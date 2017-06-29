package com.example.niraj.demoapp.Fonts;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Niraj on 18-01-2017.
 */

public class MyCustomFontsRegular extends TextView {
    public MyCustomFontsRegular(Context context) {
        super(context);
        init(context);
    }

    public MyCustomFontsRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyCustomFontsRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Fonts/Ubuntu-Regular.ttf"));
    }


}
