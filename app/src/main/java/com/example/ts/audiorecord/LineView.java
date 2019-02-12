/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.ts.audiorecord;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LineView extends View {
    public LineView(Context context) {
        this(context,null);
    }

    public LineView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        an();
    }

    Paint mPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.drawLine(0 + x,0,100,200,mPaint);
        canvas.restore();
    }
    int x;
    private void an(){
        ValueAnimator animator = ValueAnimator.ofInt(0,500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               x = (int) animation.getAnimatedValue();
               invalidate();
            }
        });
        animator.setDuration(10000);
        animator.start();
    }
}
