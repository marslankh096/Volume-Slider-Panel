package com.demo.volumeslider.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class VolumeView extends View {
    Context f13cn;
    private int max;
    OnVolumeChange onVolumeChange;
    private int progress;

    
    public interface OnVolumeChange {
        void onSeekChange(VolumeView volumeView, int i);

        void onSeekStart(VolumeView volumeView);

        void onStopStracking(VolumeView volumeView);
    }

    public VolumeView(Context context) {
        super(context);
        this.max = 380;
        this.progress = 0;
    }

    public VolumeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.max = 380;
        this.progress = 0;
    }

    public VolumeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.max = 380;
        this.progress = 0;
    }

    public VolumeView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.max = 380;
        this.progress = 0;
    }

    public void setVolumeChangeListener(OnVolumeChange onVolumeChange) {
        this.onVolumeChange = onVolumeChange;
    }

    @Override 
    public boolean onTouchEvent(MotionEvent motionEvent) {
        OnVolumeChange onVolumeChange;
        int action = motionEvent.getAction();
        int i = this.max;
        Log.e("AAA", "height : " + i);
        if (action == 0) {
            OnVolumeChange onVolumeChange2 = this.onVolumeChange;
            if (onVolumeChange2 != null) {
                onVolumeChange2.onSeekStart(this);
            }
        } else if (action == 2) {
            int y = (int) motionEvent.getY();
            if (y < 0) {
                y = 0;
            }
            if (y > i) {
                y = i;
            }
            int i2 = y - i;
            if (i2 < 0) {
                i2 = Math.abs(i2);
            }
            int i3 = (i2 * 100) / i;
            this.progress = i3;
            setProgress(i3);
            OnVolumeChange onVolumeChange3 = this.onVolumeChange;
            if (onVolumeChange3 != null) {
                onVolumeChange3.onSeekChange(this, this.progress);
            }
            Log.e("AAA", "Y : " + i2 + " Progress : " + this.progress);
        } else if (action == 1 && (onVolumeChange = this.onVolumeChange) != null) {
            onVolumeChange.onStopStracking(this);
        }
        return true;
    }

    public void setProgress(int i) {
        this.progress = i;
        getBackground().setLevel(i * 100);
    }

    public void setMax(int i) {
        this.max = i;
    }
}
