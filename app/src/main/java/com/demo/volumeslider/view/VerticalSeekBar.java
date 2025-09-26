package com.demo.volumeslider.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;


public class VerticalSeekBar extends SeekBar {
    private int f10x;
    private int f11y;
    private int f12z;
    private int f9w;
    OnSeekBarChangeListener onSeekBarChangeListener;

    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override 
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i2, i, i4, i3);
        this.f10x = i;
        this.f11y = i2;
        this.f12z = i3;
        this.f9w = i4;
    }

    @Override 
    public synchronized void setProgress(int i) {
        super.setProgress(i);
        onSizeChanged(this.f10x, this.f11y, this.f12z, this.f9w);
    }

    @Override 
    public synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i2, i);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override 
    public void onDraw(Canvas canvas) {
        canvas.rotate(-90.0f);
        canvas.translate(-getHeight(), 0.0f);
        super.onDraw(canvas);
    }

    @Override 
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isEnabled()) {
            int action = motionEvent.getAction();
            if (action == 3) {
                Log.d("AAA", "cancel");
                OnSeekBarChangeListener onSeekBarChangeListener = this.onSeekBarChangeListener;
                if (onSeekBarChangeListener != null) {
                    onSeekBarChangeListener.onStopTrackingTouch(this);
                }
            } else if (action == 0) {
                Log.d("AAA", "down");
                OnSeekBarChangeListener onSeekBarChangeListener2 = this.onSeekBarChangeListener;
                if (onSeekBarChangeListener2 != null) {
                    onSeekBarChangeListener2.onStartTrackingTouch(this);
                }
            } else if (action == 1) {
                Log.d("AAA", "up");
                OnSeekBarChangeListener onSeekBarChangeListener3 = this.onSeekBarChangeListener;
                if (onSeekBarChangeListener3 != null) {
                    onSeekBarChangeListener3.onStopTrackingTouch(this);
                }
            }
            Log.d("AAA", "move");
            getMax();
            motionEvent.getY();
            getHeight();
            setProgress(getMax() - ((int) ((getMax() * motionEvent.getY()) / getHeight())));
            onSizeChanged(getWidth(), getHeight(), 0, 0);
            return true;
        }
        return false;
    }

    public void myprogress(int i) {
        onSizeChanged(getWidth(), getHeight(), 0, 0);
        super.setProgress(i);
    }

    public void setseek(OnSeekBarChangeListener onSeekBarChangeListener) {
        this.onSeekBarChangeListener = onSeekBarChangeListener;
    }

    @Override 
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener) {
        this.onSeekBarChangeListener = onSeekBarChangeListener;
        super.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }
}
