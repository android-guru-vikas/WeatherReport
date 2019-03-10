package com.dev.weatherreport.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;

import com.dev.weatherreport.R;
import com.dev.weatherreport.WeatherApp;

import java.net.URLDecoder;

public class WeatherTextView extends android.support.v7.widget.AppCompatTextView {

    private int textSize;
    private String textColor;
    private String type;
    private String f_family;
    private BufferType mBufferType = BufferType.NORMAL;

    public WeatherTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontFaceType(context, attrs);

    }

    private void setFontFaceType(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.myCustomTextView);
        if (a == null) {
            setTypeface(WeatherApp.getInstance().droidTypeFace, Typeface.NORMAL);
            return;
        }
        this.f_family = a.getString(R.styleable.myCustomTextView_f_family);
        String f_family = a.getString(R.styleable.myCustomTextView_f_family);
        if (f_family == null) {
            setTypeface(WeatherApp.getInstance().droidTypeFace, Typeface.NORMAL);
            return;
        }

        int f_type = a.getInteger(R.styleable.myCustomTextView_f_type, 0);
    }

    public void setText(String text) {
        try {
            text = URLDecoder.decode(text, "UTF-8");
            text = Html.fromHtml(text).toString();
            super.setText(text);
        } catch (Exception e) {

        }
    }

}
