package com.learn.fixedtextedittext.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;

import com.learn.fixedtextedittext.R;


public class FixedTextEditText extends AppCompatEditText {

    private boolean mIsFixedDrawableSet;
    private String mFixedText;

    public FixedTextEditText(Context context) {
        this(context, null);
    }

    public FixedTextEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FixedTextEditText);
        mFixedText = typedArray.getString(R.styleable.FixedTextEditText_fixedText);
        typedArray.recycle();
    }

    public FixedTextEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        if (height > 0 && !mIsFixedDrawableSet) {
            float baseLine = (height - getLayout().getHeight()) / 4.0f;
            FixedTextDrawable fixedTextDrawable = new FixedTextDrawable(mFixedText, getContext(), baseLine);
            setCompoundDrawablePadding(fixedTextDrawable.getBounds().right);
            setCompoundDrawablesWithIntrinsicBounds(fixedTextDrawable, null, null, null);
            mIsFixedDrawableSet = true;
        }
    }


    private static class FixedTextDrawable extends Drawable {
        private final static float FIXED_TEXT_SIZE = 16f; //sp

        private TextPaint mPaint;
        private String mFixedText;
        private float mBaseLine;

        FixedTextDrawable(String fixedText, Context context, float baseLine) {
            mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(ContextCompat.getColor(context, android.R.color.holo_red_light));
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setTextAlign(Paint.Align.LEFT);
            mFixedText = fixedText;
            mBaseLine = baseLine;
            int textSize = ((int) (context.getResources().getDisplayMetrics().density * FIXED_TEXT_SIZE + 0.5f));
            mPaint.setTextSize(textSize);
            int textWidth = ((int) (mPaint.measureText(mFixedText) + 0.5f));
            setBounds(0, 0, textWidth, textSize);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            canvas.drawText(mFixedText, 0, getBounds().bottom + mBaseLine, mPaint);
        }

        @Override
        public void setAlpha(int alpha) {
            mPaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
            mPaint.setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}
