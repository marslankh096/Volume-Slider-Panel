package com.demo.volumeslider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;


public class MultitouchView extends View {
    private static final int SIZE = 60;
    private int[] colors;
    private SparseArray<PointF> mActivePointers;
    private Paint mPaint;
    private Paint textPaint;

    public MultitouchView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.colors = new int[]{-16776961, -16711936, -65281, ViewCompat.MEASURED_STATE_MASK, -16711681, -7829368, 0xffff0000, -12303292, -3355444, InputDeviceCompat.SOURCE_ANY};
        initView();
    }

    private void initView() {
        this.mActivePointers = new SparseArray<>();
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setColor(-16776961);
        this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        Paint paint2 = new Paint(1);
        this.textPaint = paint2;
        paint2.setTextSize(20.0f);
    }

    
    @Override 
    
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(actionIndex);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    int pointerCount = motionEvent.getPointerCount();
                    for (int i = 0; i < pointerCount; i++) {
                        PointF pointF = this.mActivePointers.get(motionEvent.getPointerId(i));
                        if (pointF != null) {
                            pointF.x = motionEvent.getX(i);
                            pointF.y = motionEvent.getY(i);
                        }
                    }
                } else if (actionMasked != 3) {
                    if (actionMasked != 5) {
                    }
                }
                invalidate();
                return true;
            }
            this.mActivePointers.remove(pointerId);
            invalidate();
            return true;
        }
        PointF pointF2 = new PointF();
        pointF2.x = motionEvent.getX(actionIndex);
        pointF2.y = motionEvent.getY(actionIndex);
        this.mActivePointers.put(pointerId, pointF2);
        invalidate();
        return true;
    }

    @Override 
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.mActivePointers.size();
        for (int i = 0; i < size; i++) {
            PointF valueAt = this.mActivePointers.valueAt(i);
            if (valueAt != null) {
                this.mPaint.setColor(this.colors[i % 9]);
            }
            canvas.drawCircle(valueAt.x, valueAt.y, 60.0f, this.mPaint);
        }
        canvas.drawText("Total pointers: " + this.mActivePointers.size(), 10.0f, 40.0f, this.textPaint);
    }
}
