package com.trungpt.downloadmaster.ui.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Trung on 10/26/2015.
 */
public class CustomFontTextView extends TextView
{
    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public CustomFontTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CustomFontTextView(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/alpha_echo.ttf");
        setTypeface(tf);
    }
}
