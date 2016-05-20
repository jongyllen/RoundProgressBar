package com.f10.utils.ui.customcomponent;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by Jon on 2016-05-03.
 */
public class CircularProgressBar extends ProgressBar {

    private final int default_text_color = Color.parseColor("#616D76");
    private final int default_text_size = 20;

    private int progressTextColor;
    private int totalTextColor;
    private int threshold1Color;
    private int threshold2Color;
    private int threshold3Color;
    private int threshold1=0;
    private int threshold2=0;
    private int threshold3=0;

    private String mProgressText = "";
    private String mTotalText = "";

    private final Paint mProgressTextPaint = new Paint();
    private final Paint mTotalTextPaint = new Paint();

    public CircularProgressBar(Context context) {
        super(context);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(null, R.styleable.CircularProgressBar, 0, 0);
        init(attributes);
        attributes.recycle();
    }

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircularProgressBar, 0, 0);
        init(attributes);
        attributes.recycle();
    }

    public CircularProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircularProgressBar, defStyle, 0);
        init(attributes);
        attributes.recycle();
    }

    public void init(TypedArray attributes){
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        progressTextColor = attributes.getColor(R.styleable.CircularProgressBar_progressTextColor, default_text_color);
        mProgressTextPaint.setColor(progressTextColor);
        mProgressTextPaint.setTextSize(attributes.getInt(R.styleable.CircularProgressBar_progressTextSize, default_text_size));
        mProgressTextPaint.setStyle(Paint.Style.FILL);
        mProgressTextPaint.setAntiAlias(true);
        mProgressTextPaint.setTypeface(Typeface.create("Roboto-Thin", Typeface.BOLD));

        totalTextColor = attributes.getColor(R.styleable.CircularProgressBar_totalTextColor, default_text_color);
        mTotalTextPaint.setColor(totalTextColor);
        mTotalTextPaint.setTextSize(attributes.getInt(R.styleable.CircularProgressBar_totalTextSize, default_text_size));
        mTotalTextPaint.setStyle(Paint.Style.FILL);
        mTotalTextPaint.setAntiAlias(true);
        mTotalTextPaint.setTypeface(Typeface.create("Roboto-Thin", Typeface.BOLD));

        threshold1Color = attributes.getColor(R.styleable.CircularProgressBar_threshold1Color, default_text_color);
        threshold2Color = attributes.getColor(R.styleable.CircularProgressBar_threshold2Color, default_text_color);
        threshold3Color = attributes.getColor(R.styleable.CircularProgressBar_threshold3Color, default_text_color);

        threshold1 = attributes.getInt(R.styleable.CircularProgressBar_threshold1Value, threshold1);
        threshold2 = attributes.getInt(R.styleable.CircularProgressBar_threshold2Value, threshold2);
        threshold3 = attributes.getInt(R.styleable.CircularProgressBar_threshold3Value, threshold3);

        mTotalText = String.valueOf(this.getMax());

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        if(!TextUtils.isEmpty(mProgressText)){
            int xPos =  (int)(getMeasuredWidth()/2 - mProgressTextPaint.measureText(mProgressText) / 2);
            int yPos = getMeasuredHeight()/2;

            float titleHeight = Math.abs(mProgressTextPaint.descent() + mProgressTextPaint.ascent());
            if(TextUtils.isEmpty(mTotalText)){
                yPos += titleHeight/2;
            }
            canvas.drawText(mProgressText, xPos, yPos-10, mProgressTextPaint);

            yPos += titleHeight;
            xPos = (int)(getMeasuredWidth()/2 - mTotalTextPaint.measureText(mTotalText) / 2);

            canvas.drawText(mTotalText, xPos, yPos+10, mTotalTextPaint);
        }

        if(this.getProgress() <= threshold1) {
            ColorStateList stateList = ColorStateList.valueOf(threshold1Color);
            this.setProgressTintList(stateList);
        }

        if(this.getProgress() > threshold1 && this.getProgress() <= threshold2) {
            ColorStateList stateList = ColorStateList.valueOf(threshold2Color);
            this.setProgressTintList(stateList);
        }

        if(this.getProgress() > threshold2 && this.getProgress() < threshold3) {
            ColorStateList stateList = ColorStateList.valueOf(threshold3Color);
            this.setProgressTintList(stateList);
        }

        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height);
        //setMeasuredDimension(min+2*STROKE_WIDTH, min+2*STROKE_WIDTH);
        setMeasuredDimension(min, min);
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        mProgressText = String.valueOf(progress);
        invalidate();
    }


}
