package com.app.studymanager.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.app.studymanager.R;

/**
 * Created by Vinay on 25-10-2016.
 */

public class Circle extends View {
    private static final int START_ANGLE_POINT = 270;

    private static int DEFAULT_TITLE_COLOR = R.color.colorPrimary;
    private static int DEFAULT_SUBTITLE_COLOR = R.color.colorPrimary;

    private static String DEFAULT_TITLE = "0%";
    private static String DEFAULT_SUBTITLE = "COMPLETE";

    private static boolean DEFAULT_SHOW_TITLE = true;
    private static boolean DEFAULT_SHOW_SUBTITLE = true;

    private static float DEFAULT_TITLE_SIZE = 50f;
    private static float DEFAULT_SUBTITLE_SIZE = 40f;
    private static float DEFAULT_TITLE_SUBTITLE_SPACE = 25f;

    private static int DEFAULT_STROKE_COLOR = R.color.colorPrimary;
    private static int DEFAULT_BACKGROUND_COLOR = Color.WHITE;

    private static float DEFAULT_STROKE_WIDTH = 10f;

    private static final int DEFAULT_VIEW_SIZE = 100;

    private Paint paint;
    private Paint defaultPaint;
    private RectF rect;

    private float angle;

    private int mTitleColor = DEFAULT_TITLE_COLOR;
    private int mSubtitleColor = DEFAULT_SUBTITLE_COLOR;
    private int mStrokeColor = DEFAULT_STROKE_COLOR;
    private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;

    private String mTitleText = DEFAULT_TITLE;
    private String mSubtitleText = DEFAULT_SUBTITLE;

    private float mTitleSize = DEFAULT_TITLE_SIZE;
    private float mSubtitleSize = DEFAULT_SUBTITLE_SIZE;
    private float mStrokeWidth = DEFAULT_STROKE_WIDTH;
    private float mTitleSubtitleSpace = DEFAULT_TITLE_SUBTITLE_SPACE;

    private boolean mShowTitle = DEFAULT_SHOW_TITLE;
    private boolean mShowSubtitle = DEFAULT_SHOW_SUBTITLE;

    private TextPaint mTitleTextPaint;
    private TextPaint mSubTextPaint;

    private Paint mStrokePaint;
    private Paint mBackgroundPaint;

    private RectF mInnerRectF;

    private Bitmap bitmap;

    private int mViewSize;

    private Context context;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public void init(AttributeSet attrs, int defStyle){

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mStrokeWidth);
        paint.setColor(ContextCompat.getColor(context, R.color.light_green));

        defaultPaint = new Paint();
        defaultPaint.setAntiAlias(true);
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setStrokeWidth(mStrokeWidth);
        defaultPaint.setColor(Color.LTGRAY);

        mInnerRectF = new RectF();

        //size 200x200 example
        rect = new RectF(mStrokeWidth, mStrokeWidth, 200 + mStrokeWidth, 200 + mStrokeWidth);

        //Initial Angle (optional, it can be zero)
        angle = 0;

        mTitleTextPaint = new TextPaint();
        mTitleTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTitleTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mTitleTextPaint.setTextAlign(Paint.Align.CENTER);
        mTitleTextPaint.setLinearText(true);
        mTitleTextPaint.setColor(ContextCompat.getColor(context, R.color.light_green));
        mTitleTextPaint.setTextSize(mTitleSize);

        mSubTextPaint = new TextPaint();
        mSubTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mSubTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mSubTextPaint.setTextAlign(Paint.Align.CENTER);
        mSubTextPaint.setLinearText(true);
        mSubTextPaint.setColor(ContextCompat.getColor(context, R.color.light_green));
        mSubTextPaint.setTextSize(mSubtitleSize);

        mStrokePaint = new Paint();
        mStrokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(ContextCompat.getColor(context, R.color.light_green));
        mStrokePaint.setStrokeWidth(mStrokeWidth);

        bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.subscribed);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = resolveSize(DEFAULT_VIEW_SIZE, widthMeasureSpec);
        //int height = resolveSize(DEFAULT_VIEW_SIZE, heightMeasureSpec);
        int height = width;
        mViewSize = Math.min(width, height);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        mInnerRectF.set(0, 0, mViewSize, mViewSize);
        mInnerRectF.offset((getWidth() - mViewSize) / 2, (getHeight() - mViewSize) / 2);

        final int halfBorder = (int) (mStrokePaint.getStrokeWidth() / 2f + 0.5f);

        mInnerRectF.inset(halfBorder, halfBorder);

        float centerX = mInnerRectF.centerX();
        float centerY = mInnerRectF.centerY();

        canvas.drawArc(mInnerRectF, 0, 360, false, defaultPaint);
        canvas.drawArc(mInnerRectF, START_ANGLE_POINT, angle, false, paint);

        //canvas.drawArc(rect, 0, 360, false, defaultPaint);
        //canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);

        int xPos = (int) centerX;
        int yPos = (int) (centerY - (mTitleTextPaint.descent() + mTitleTextPaint.ascent()) / 2);

        canvas.drawText(mTitleText,
                xPos,
                yPos - 5,
                mTitleTextPaint);

        canvas.drawText(mSubtitleText,
                xPos,
                yPos + mTitleSubtitleSpace + 5,
                mSubTextPaint);

        //canvas.drawBitmap(bitmap, xPos, yPos + 50, null);
       // bitmap.recycle();


    }

    private void invalidateTextPaints(){
        mTitleTextPaint.setColor(mTitleColor);
        mSubTextPaint.setColor(mSubtitleColor);
        mTitleTextPaint.setTextSize(mTitleSize);
        mSubTextPaint.setTextSize(mSubtitleSize);
        invalidate();
    }

    private void invalidatePaints(){
        mBackgroundPaint.setColor(mBackgroundColor);
        mStrokePaint.setColor(mStrokeColor);
        invalidate();
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getmViewSize() {
        return mViewSize;
    }

    public void setmViewSize(int mViewSize) {
        this.mViewSize = mViewSize;
    }

    public int getmTitleColor() {
        return mTitleColor;
    }

    public void setmTitleColor(int mTitleColor) {
        this.mTitleColor = mTitleColor;
        invalidateTextPaints();
    }

    public int getmSubtitleColor() {
        return mSubtitleColor;
    }

    public void setmSubtitleColor(int mSubtitleColor) {
        this.mSubtitleColor = mSubtitleColor;
        invalidateTextPaints();
    }

    public int getmStrokeColor() {
        return mStrokeColor;
    }

    public void setmStrokeColor(int mStrokeColor) {
        this.mStrokeColor = mStrokeColor;
        invalidatePaints();
    }

    public int getmBackgroundColor() {
        return mBackgroundColor;
    }

    public void setmBackgroundColor(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
        invalidatePaints();
    }

    public String getmTitleText() {
        return mTitleText;
    }

    public void setmTitleText(String mTitleText) {
        this.mTitleText = mTitleText;
        invalidate();
    }

    public String getmSubtitleText() {
        return mSubtitleText;
    }

    public void setmSubtitleText(String mSubtitleText) {
        this.mSubtitleText = mSubtitleText;
        invalidate();
    }

    public float getmTitleSize() {
        return mTitleSize;
    }

    public void setmTitleSize(float mTitleSize) {
        this.mTitleSize = mTitleSize;
        invalidateTextPaints();
    }

    public float getmSubtitleSize() {
        return mSubtitleSize;
    }

    public void setmSubtitleSize(float mSubtitleSize) {
        this.mSubtitleSize = mSubtitleSize;
        invalidateTextPaints();
    }

    public float getmStrokeWidth() {
        return mStrokeWidth;
    }

    public void setmStrokeWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }

    public float getmTitleSubtitleSpace() {
        return mTitleSubtitleSpace;
    }

    public void setmTitleSubtitleSpace(float mTitleSubtitleSpace) {
        this.mTitleSubtitleSpace = mTitleSubtitleSpace;
        invalidateTextPaints();
    }

    public boolean ismShowTitle() {
        return mShowTitle;
    }

    public void setmShowTitle(boolean mShowTitle) {
        this.mShowTitle = mShowTitle;
        invalidate();
    }

    public boolean ismShowSubtitle() {
        return mShowSubtitle;
    }

    public void setmShowSubtitle(boolean mShowSubtitle) {
        this.mShowSubtitle = mShowSubtitle;
        invalidate();
    }

    public TextPaint getmTitleTextPaint() {
        return mTitleTextPaint;
    }

    public void setmTitleTextPaint(TextPaint mTitleTextPaint) {
        this.mTitleTextPaint = mTitleTextPaint;
    }

    public TextPaint getmSubTextPaint() {
        return mSubTextPaint;
    }

    public void setmSubTextPaint(TextPaint mSubTextPaint) {
        this.mSubTextPaint = mSubTextPaint;
    }

    public Paint getmStrokePaint() {
        return mStrokePaint;
    }

    public void setmStrokePaint(Paint mStrokePaint) {
        this.mStrokePaint = mStrokePaint;
    }

    public Paint getmBackgroundPaint() {
        return mBackgroundPaint;
    }

    public void setmBackgroundPaint(Paint mBackgroundPaint) {
        this.mBackgroundPaint = mBackgroundPaint;
    }

    public RectF getmInnerRectF() {
        return mInnerRectF;
    }

    public void setmInnerRectF(RectF mInnerRectF) {
        this.mInnerRectF = mInnerRectF;
    }
}
