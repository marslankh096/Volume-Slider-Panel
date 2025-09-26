package com.demo.volumeslider;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.rtugeek.android.colorseekbar.ColorSeekBar;
import com.xw.repo.BubbleSeekBar;
import com.demo.volumeslider.service.MyAccessibilityService;
import com.demo.volumeslider.view.Helper;


public class SettingActivity extends AppCompatActivity {
    int al;
    int bg;
    Button btnleft;
    Button btnright;
    ColorSeekBar colorBack;
    ColorSeekBar colorProgress;
    ColorSeekBar colorSeekBar;
    long currentTimeout = 3000;
    int currentverticalpos = 50;
    Context f110cn = this;
    int ic;
    int pb;
    BubbleSeekBar seekVerticalPos;
    BubbleSeekBar seektimeout;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_setting);


        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd_Counter(this);


        new Utl(this);
        init();
        click();
        managleftRight();
        this.seekVerticalPos.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() { 
            @Override 
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int i, float f) {
            }

            @Override 
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int i, float f, boolean z) {
            }

            @Override 
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int i, float f, boolean z) {
                SettingActivity.this.currentverticalpos = i;
                Utl.editor.putInt("y", SettingActivity.this.currentverticalpos);
                Utl.editor.commit();
            }
        });
        int i = Utl.pref.getInt("y", 50);
        this.currentverticalpos = i;
        this.seekVerticalPos.setProgress(i);
        this.seektimeout.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() { 
            @Override 
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int i2, float f) {
            }

            @Override 
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int i2, float f, boolean z) {
            }

            @Override 
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int i2, float f, boolean z) {
                long j = i2 * 1000;
                if (j < 1000) {
                    j = 500;
                }
                Utl.editor.putLong("timeout", j);
                Utl.editor.commit();
            }
        });
        long j = Utl.pref.getLong("timeout", 3000L);
        this.currentTimeout = j;
        this.seektimeout.setProgress((int) (j / 1000));
        this.colorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() { 
            @Override 
            public void onColorChangeListener(int i2, int i3, int i4) {
                Utl.editor.putInt("icon", i4);
                Utl.editor.putInt("iconPosition", i2);
                Utl.editor.commit();
                MyAccessibilityService.viewCrewate();
            }
        });
        this.colorBack.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() { 
            @Override 
            public void onColorChangeListener(int i2, int i3, int i4) {
                Utl.editor.putInt("back", i4);
                Utl.editor.putInt("alpha", SettingActivity.this.colorSeekBar.getAlphaValue());
                Utl.editor.putInt("backPosition", i2);
                Utl.editor.putInt("AbackPosition", i2);
                Utl.editor.commit();
                MyAccessibilityService.viewCrewate();
            }
        });
        this.colorProgress.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() { 
            @Override 
            public void onColorChangeListener(int i2, int i3, int i4) {
                Utl.editor.putInt(NotificationCompat.CATEGORY_PROGRESS, i4);
                Utl.editor.putInt("bPposition", i2);
                Utl.editor.commit();
                MyAccessibilityService.viewCrewate();
            }
        });
    }

    public void managleftRight() {
        new Utl(this.f110cn);
        if (Utl.pref.getBoolean("isleft", false)) {
            this.btnleft.setBackground(getResources().getDrawable(R.drawable.btnfill));
            this.btnright.setBackground(getResources().getDrawable(R.drawable.btnemptyr));
            return;
        }
        this.btnleft.setBackground(getResources().getDrawable(R.drawable.btnemptyl));
        this.btnright.setBackground(getResources().getDrawable(R.drawable.btnfillr));
    }

    private void click() {
        this.btnleft.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Utl.editor.putBoolean("isleft", true);
                Utl.editor.commit();
                SettingActivity.this.managleftRight();
            }
        });
        this.btnright.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Utl.editor.putBoolean("isleft", false);
                Utl.editor.commit();
                SettingActivity.this.managleftRight();
            }
        });
    }

    public static Bitmap createThumb(Context context, Bitmap bitmap) {
        int height = (context.getResources().getDisplayMetrics().widthPixels * bitmap.getHeight()) / 1080;
        return Bitmap.createScaledBitmap(bitmap, height, height, true);
    }

    private void init() {
        this.seekVerticalPos = (BubbleSeekBar) findViewById(R.id.seekVerticalPos);
        this.seektimeout = (BubbleSeekBar) findViewById(R.id.seektimeout);
        this.btnleft = (Button) findViewById(R.id.btnleft);
        this.btnright = (Button) findViewById(R.id.btnright);
        this.colorSeekBar = (ColorSeekBar) findViewById(R.id.colorSlider);
        this.colorBack = (ColorSeekBar) findViewById(R.id.bg_slider);
        this.colorProgress = (ColorSeekBar) findViewById(R.id.colorprogress);
        this.colorSeekBar.setMaxPosition(100);
        this.colorSeekBar.setColorSeeds(R.array.text_colors);
        this.colorSeekBar.setColorBarPosition(10);
        this.colorSeekBar.setAlphaBarPosition(0);
        this.colorSeekBar.setPosition(10, 10);
        this.colorSeekBar.setShowAlphaBar(true);
        this.colorBack.setMaxPosition(100);
        this.colorBack.setColorSeeds(R.array.text_colors);
        this.colorBack.setColorBarPosition(0);
        this.colorBack.setAlphaBarPosition(10);
        this.colorBack.setPosition(10, 10);
        this.colorProgress.setMaxPosition(100);
        this.colorProgress.setColorSeeds(R.array.text_colors);
        this.colorProgress.setColorBarPosition(0);
        this.colorProgress.setAlphaBarPosition(10);
        this.colorProgress.setPosition(10, 10);
        if (Helper.check == 3) {
            this.ic = Utl.pref.getInt("iconPosition", 0);
            this.bg = Utl.pref.getInt("backPosition", 0);
            this.al = Utl.pref.getInt("AbackPosition", 0);
            this.pb = Utl.pref.getInt("bPposition", 0);
            this.colorSeekBar.setColorBarPosition(this.ic);
            this.colorBack.setColorBarPosition(this.bg);
            this.colorProgress.setColorBarPosition(this.pb);
            this.colorBack.setAlphaBarPosition(this.al);
        }
    }
}
