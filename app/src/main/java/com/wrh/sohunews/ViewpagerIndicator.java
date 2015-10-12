package com.wrh.sohunews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/1/20.
 */
public class ViewpagerIndicator extends LinearLayout {

    /**
     * 画笔，底部矩形
     */
    private Paint paint;
    /**
     * 子View个数
     */
    private int mCount;
    private int mTop;
    private int mWidth;
    private int mLeft;
    private int mHeight = 5;
    public OnTextClick onTextClick;
    public ViewpagerIndicator(Context context) {
        super(context);
    }

    public ViewpagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.TRANSPARENT);
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.red));
        paint.setAntiAlias(true);

    }

    public interface OnTextClick{
       public void textViewClick(int position);
    }

    public void setOnTextClick(OnTextClick onTextClick){
        this.onTextClick = onTextClick;

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //整体高度
        mTop = getMeasuredHeight();
        //整体宽度
        int width = getMeasuredWidth();
        //加上指示器高度
        int height = mTop + mHeight;
        //指示器的宽度
        mWidth = width / mCount;
        setMeasuredDimension(width, height);
    }

    /**
     * 选中状态字体变色
     * @param position
     */
    protected void setFocusTextView(int position)
    {
        View view = getChildAt(position);
        if (view instanceof TextView)
        {
            ((TextView) view).setTextColor(getResources().getColor(R.color.red));
        }

    }

    /**
     * 重置字体颜色
     */
    protected void resetTextViewColor()
    {
        for (int i = 0; i < getChildCount(); i++)
        {
            View view = getChildAt(i);
            if (view instanceof TextView)
            {
                ((TextView) view).setTextColor(getResources().getColor(R.color.black));
            }
        }

    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCount = getChildCount();

        /**
         * 监听点击事件，回调接口
         */
        for (int i = 0 ;i<mCount;i++){
            View view = getChildAt(i);
            if (view instanceof TextView)
            {
                final int finalI = i;
                ((TextView) view).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         if (onTextClick != null) {
                             onTextClick.textViewClick(finalI);
                         }
                    }
                });
            }
        }
    }

    /**
     * 滚动效果
     * @param position
     * @param positionOffset
     */
    public void scrollTo(int position, float positionOffset) {
        mLeft = (int) ((position + positionOffset) * mWidth);
        postInvalidate();
    }

    public int getIndicatorWidth() {
        return mWidth;
    }

    /**
     * 画出指示器
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        Rect rect = new Rect(mLeft, mTop, mLeft + mWidth, mTop + mHeight);
        canvas.drawRect(rect, paint);
    }


}
