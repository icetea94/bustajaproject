package com.devshyeon.bustaja;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class MyFontTextView extends AppCompatTextView {

    //생성자 2 xml로 만들 때 사용
    public MyFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface= Typeface.createFromAsset(context.getAssets(),"fonts/CookieRun Bold.otf");
        setTypeface(typeface);

    }


    //생성자 1 자바로 만들 때 사용
    public MyFontTextView(Context context) {
        super(context);

        //폰트 설정을 생성자에서
        Typeface typeface= Typeface.createFromAsset(context.getAssets(),"fonts/CookieRun Bold.otf");
        setTypeface(typeface);


    }

}
