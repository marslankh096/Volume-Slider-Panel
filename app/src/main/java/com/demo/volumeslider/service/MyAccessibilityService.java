package com.demo.volumeslider.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.demo.volumeslider.R;

import com.demo.volumeslider.Utl;
import com.demo.volumeslider.view.Helper;
import com.demo.volumeslider.view.VolumeView;


public class MyAccessibilityService extends AccessibilityService {
    public static AudioManager audioManager = null;
    static int bg_color = 0;
    static ImageView btn_controll = null;
    static ImageView btn_ringmode = null;
    static ImageView btn_tune = null;
    static ImageView btnmore = null;
    static CardView cardView1 = null;
    static CardView cardView2 = null;
    static CardView cardView3 = null;
    public static int check = 1;
    private static int cur;
    static int currentAlarm;
    static int currentCall;
    static int currentMusic;
    static int currentRing;
    static View extrabtn;
    public static View floatView;
    static LinearLayout floats;
    static Runnable hideRunnable;
    static int ic_color;
    static LinearLayout layMusic;
    static LinearLayout laymain;
    static LinearLayout layring;
    static LinearLayout layseek;
    static LinearLayout laysub;
    static Context mContext;
    public static WindowManager mWindowManager;
    public static WindowManager.LayoutParams params;
    static int pb_color;
    public static int screenH;
    public static int screenW;
    static SeekBar seekAlarm;
    static SeekBar seekMusic;
    static SeekBar seekRing;
    static SeekBar seekcall;
    static VolumeView vseekAlarm;
    static VolumeView vseekMusic;
    static VolumeView vseekRing;
    static VolumeView vseekcall;
    RingtoneManager ringtoneManager;
    static Handler hideHandler = new Handler();
    static int maxAlarm = 0;
    static int maxCall = 0;
    static int maxMusic = 0;
    public static int maxProgressVal = 15;
    static int maxRing = 0;
    public static long timeOut = 3000;
    public static boolean hasRun = false;

    public static void viewCrewate() {
        View view;
        destroyView();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, Build.VERSION.SDK_INT >= 26 ? 2038 : 2010, 8, -3);
        params = layoutParams;
        layoutParams.gravity = 51;
        int pixw = screenW - getPixw(150);
        if (Utl.pref.getBoolean("isleft", false)) {
            params.x = 0;
        } else {
            params.x = pixw;
        }
        new Utl(mContext);
        params.y = ((screenH - getPixh(618)) * Utl.pref.getInt("y", 50)) / 100;
        addManageView();
        WindowManager windowManager = mWindowManager;
        if (windowManager == null || (view = floatView) == null) {
            Log.e("Volume", "View or manager null");
            return;
        }
        try {
            windowManager.addView(view, params);
        } catch (Exception unused) {
            Log.e("Volume", "View add exception");
        }
        LinearLayout linearLayout = laymain;
        if (linearLayout != null) {
            linearLayout.setVisibility(View.GONE);
        }
    }

    private static void addManageView() {
        Context context = mContext;
        if (context != null) {
            AudioManager audioManager2 = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager = audioManager2;
            maxRing = audioManager2.getStreamMaxVolume(2);
            currentRing = audioManager.getStreamVolume(2);
            maxCall = audioManager.getStreamMaxVolume(0);
            currentCall = audioManager.getStreamVolume(0);
            maxMusic = audioManager.getStreamMaxVolume(3);
            currentMusic = audioManager.getStreamVolume(3);
            maxAlarm = audioManager.getStreamMaxVolume(4);
            currentAlarm = audioManager.getStreamVolume(4);
            new Utl(mContext);
            ApplyStyle();
            btn_ringmode = (ImageView) floatView.findViewById(R.id.ringmode);
            laymain = (LinearLayout) floatView.findViewById(R.id.laymain);
            laysub = (LinearLayout) floatView.findViewById(R.id.laysub);
            layseek = (LinearLayout) floatView.findViewById(R.id.layseek);
            layring = (LinearLayout) floatView.findViewById(R.id.layring);
            layMusic = (LinearLayout) floatView.findViewById(R.id.laymusic);
            btnmore = (ImageView) floatView.findViewById(R.id.btnmore);
            btn_tune = (ImageView) floatView.findViewById(R.id.btn_tune);
            laymain.setVisibility(View.GONE);
            laysub.setVisibility(View.GONE);
            btn_tune.setVisibility(View.GONE);
            btnmore.setImageResource(R.drawable.ic_baseline_navigate_before_24);
            ImageView imageView = (ImageView) floatView.findViewById(R.id.btnring);
            ImageView imageView2 = (ImageView) floatView.findViewById(R.id.btnmusic);
            ImageView imageView3 = (ImageView) floatView.findViewById(R.id.btnalarm);
            ImageView imageView4 = (ImageView) floatView.findViewById(R.id.btncall);
            cardView1 = (CardView) floatView.findViewById(R.id.main_bg1);
            cardView2 = (CardView) floatView.findViewById(R.id.main_bg2);
            cardView3 = (CardView) floatView.findViewById(R.id.main_bg3);
            if (Helper.check == 3) {
                ic_color = Utl.pref.getInt("icon", 0);
                bg_color = Utl.pref.getInt("back", 0);
                pb_color = Utl.pref.getInt(NotificationCompat.CATEGORY_PROGRESS, 0);
                imageView.setColorFilter(ic_color);
                imageView2.setColorFilter(ic_color);
                imageView3.setColorFilter(ic_color);
                imageView4.setColorFilter(ic_color);
                btnmore.setColorFilter(ic_color);
                btn_tune.setColorFilter(ic_color);
                btn_ringmode.setColorFilter(ic_color);
                cardView1.setCardBackgroundColor(bg_color);
                cardView2.setCardBackgroundColor(bg_color);
                cardView3.setCardBackgroundColor(bg_color);
                ContextCompat.getDrawable(mContext, R.drawable.fill).setColorFilter(new PorterDuffColorFilter(pb_color, PorterDuff.Mode.SRC_IN));
                ContextCompat.getDrawable(mContext, R.drawable.fill3).setColorFilter(new PorterDuffColorFilter(pb_color, PorterDuff.Mode.SRC_IN));
            }
            manageVolumeViews(cur);
            btn_ringmode.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    MyAccessibilityService.setNewRingerMode();
                }
            });
            btnmore.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    int i = Integer.parseInt(String.valueOf(view.getTag())) == 0 ? 1 : 0;
                    if (i == 0) {
                        MyAccessibilityService.laysub.setVisibility(View.GONE);
                        MyAccessibilityService.btn_tune.setVisibility(View.GONE);
                        if (MyAccessibilityService.audioManager.isMusicActive()) {
                            MyAccessibilityService.layMusic.setVisibility(View.GONE);
                        } else {
                            MyAccessibilityService.layring.setVisibility(View.GONE);
                        }
                        if (Utl.pref.getBoolean("isleft", false)) {
                            MyAccessibilityService.params.x = 0;
                        }
                        MyAccessibilityService.btnmore.setImageResource(R.drawable.ic_baseline_navigate_before_24);
                    } else {
                        MyAccessibilityService.getPixw(552);
                        if (Utl.pref.getBoolean("isleft", false)) {
                            MyAccessibilityService.params.x = 0;
                        }
                        MyAccessibilityService.laysub.setVisibility(View.VISIBLE);
                        MyAccessibilityService.layMusic.setVisibility(View.VISIBLE);
                        MyAccessibilityService.layring.setVisibility(View.VISIBLE);
                        MyAccessibilityService.btn_tune.setVisibility(View.VISIBLE);
                        MyAccessibilityService.btnmore.setImageResource(R.drawable.ic_baseline_navigate_next_24);
                    }
                    MyAccessibilityService.hideafterTime();
                    MyAccessibilityService.btnmore.setTag(Integer.valueOf(i));
                    try {
                        MyAccessibilityService.mWindowManager.updateViewLayout(MyAccessibilityService.floatView, MyAccessibilityService.params);
                    } catch (Exception e) {
                        Log.e("Aaa", "More click update x error : " + e.getMessage());
                    }
                }
            });
            btn_tune.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    Intent intent = new Intent("android.settings.SOUND_SETTINGS");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyAccessibilityService.mContext.startActivity(intent);
                }
            });
            resize();
        }
    }

    private static void manageVolumeViews(int i) {
        if ((Helper.check == 0 && (i == 0 || i == 1 || i == 2 || i == 3)) || ((Helper.check == 1 && (i == 0 || i == 1 || i == 2 || i == 3)) || ((Helper.check == 2 && (i == 0 || i == 1)) || (Helper.check == 3 && (i == 2 || i == 3))))) {
            maxProgressVal = 100;
            vseekRing = (VolumeView) floatView.findViewById(R.id.seekring);
            vseekMusic = (VolumeView) floatView.findViewById(R.id.seekmusic);
            vseekAlarm = (VolumeView) floatView.findViewById(R.id.seekAlarm);
            vseekcall = (VolumeView) floatView.findViewById(R.id.seekcall);
            vseekRing.setMax(400);
            vseekMusic.setMax(400);
            vseekAlarm.setMax(400);
            vseekcall.setMax(400);
            vseekRing.setVolumeChangeListener(new VolumeView.OnVolumeChange() { 
                @Override 
                public void onSeekStart(VolumeView volumeView) {
                    Log.d("Volume", "onStartTrackingTouch");
                    if (MyAccessibilityService.hideRunnable != null) {
                        MyAccessibilityService.hideHandler.removeCallbacks(MyAccessibilityService.hideRunnable);
                    }
                }

                @Override 
                public void onSeekChange(VolumeView volumeView, int i2) {
                    int i3 = (MyAccessibilityService.maxRing * i2) / MyAccessibilityService.maxProgressVal;
                    Log.e("Volume", "Ring : " + i3);
                    MyAccessibilityService.audioManager.setStreamVolume(2, i3, 0);
                }

                @Override 
                public void onStopStracking(VolumeView volumeView) {
                    Log.d("Volume", "onStopTrackingTouch");
                    if (MyAccessibilityService.hideRunnable != null) {
                        MyAccessibilityService.hideHandler.postDelayed(MyAccessibilityService.hideRunnable, MyAccessibilityService.timeOut);
                    }
                }
            });
            vseekRing.setProgress((maxProgressVal * currentRing) / maxRing);
            vseekcall.setVolumeChangeListener(new VolumeView.OnVolumeChange() { 
                @Override 
                public void onSeekStart(VolumeView volumeView) {
                    Log.d("Volume", "onStartTrackingTouch");
                    if (MyAccessibilityService.hideRunnable != null) {
                        MyAccessibilityService.hideHandler.removeCallbacks(MyAccessibilityService.hideRunnable);
                    }
                }

                @Override 
                public void onSeekChange(VolumeView volumeView, int i2) {
                    int i3 = (MyAccessibilityService.maxCall * i2) / MyAccessibilityService.maxProgressVal;
                    Log.e("Volume", "CALL : " + i3);
                    MyAccessibilityService.audioManager.setStreamVolume(0, i3, 0);
                }

                @Override 
                public void onStopStracking(VolumeView volumeView) {
                    Log.d("Volume", "onStopTrackingTouch");
                    if (MyAccessibilityService.hideRunnable != null) {
                        MyAccessibilityService.hideHandler.postDelayed(MyAccessibilityService.hideRunnable, MyAccessibilityService.timeOut);
                    }
                }
            });
            vseekcall.setProgress((maxProgressVal * currentCall) / maxCall);
            vseekMusic.setVolumeChangeListener(new VolumeView.OnVolumeChange() { 
                @Override 
                public void onSeekStart(VolumeView volumeView) {
                    if (MyAccessibilityService.hideRunnable != null) {
                        MyAccessibilityService.hideHandler.removeCallbacks(MyAccessibilityService.hideRunnable);
                    }
                }

                @Override 
                public void onSeekChange(VolumeView volumeView, int i2) {
                    int i3 = (MyAccessibilityService.maxMusic * i2) / MyAccessibilityService.maxProgressVal;
                    Log.e("VVV", "Vol : " + i3);
                    MyAccessibilityService.audioManager.setStreamVolume(3, i3, 0);
                }

                @Override 
                public void onStopStracking(VolumeView volumeView) {
                    if (MyAccessibilityService.hideRunnable != null) {
                        MyAccessibilityService.hideHandler.postDelayed(MyAccessibilityService.hideRunnable, MyAccessibilityService.timeOut);
                    }
                }
            });
            vseekMusic.setProgress((maxProgressVal * currentMusic) / maxMusic);
            vseekAlarm.setVolumeChangeListener(new VolumeView.OnVolumeChange() { 
                @Override 
                public void onSeekStart(VolumeView volumeView) {
                    if (MyAccessibilityService.hideRunnable != null) {
                        MyAccessibilityService.hideHandler.removeCallbacks(MyAccessibilityService.hideRunnable);
                    }
                }

                @Override 
                public void onSeekChange(VolumeView volumeView, int i2) {
                    int i3 = (MyAccessibilityService.maxAlarm * i2) / MyAccessibilityService.maxProgressVal;
                    Log.e("Volume", "Alarm Vol : " + i3);
                    MyAccessibilityService.audioManager.setStreamVolume(4, i3, 0);
                }

                @Override 
                public void onStopStracking(VolumeView volumeView) {
                    if (MyAccessibilityService.hideRunnable != null) {
                        MyAccessibilityService.hideHandler.postDelayed(MyAccessibilityService.hideRunnable, MyAccessibilityService.timeOut);
                    }
                }
            });
            vseekAlarm.setProgress((maxProgressVal * currentAlarm) / maxAlarm);
            return;
        }
        maxProgressVal = 15;
        seekRing = (SeekBar) floatView.findViewById(R.id.seekring);
        seekMusic = (SeekBar) floatView.findViewById(R.id.seekmusic);
        seekAlarm = (SeekBar) floatView.findViewById(R.id.seekAlarm);
        seekcall = (SeekBar) floatView.findViewById(R.id.seekcall);
        seekRing.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                int i3 = (MyAccessibilityService.maxRing * i2) / MyAccessibilityService.maxProgressVal;
                Log.e("Volume", "Ring : " + i3);
                MyAccessibilityService.audioManager.setStreamVolume(2, i3, 0);
            }

            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("Volume", "onStartTrackingTouch");
                if (MyAccessibilityService.hideRunnable != null) {
                    MyAccessibilityService.hideHandler.removeCallbacks(MyAccessibilityService.hideRunnable);
                }
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("Volume", "onStopTrackingTouch");
                if (MyAccessibilityService.hideRunnable != null) {
                    MyAccessibilityService.hideHandler.postDelayed(MyAccessibilityService.hideRunnable, MyAccessibilityService.timeOut);
                }
            }
        });
        seekRing.setProgress((maxProgressVal * currentRing) / maxRing);
        seekcall.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                int i3 = (MyAccessibilityService.maxCall * i2) / MyAccessibilityService.maxProgressVal;
                Log.e("Volume", "CAll : " + i3);
                MyAccessibilityService.audioManager.setStreamVolume(0, i3, 0);
            }

            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("Volume", "onStartTrackingTouch");
                if (MyAccessibilityService.hideRunnable != null) {
                    MyAccessibilityService.hideHandler.removeCallbacks(MyAccessibilityService.hideRunnable);
                }
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("Volume", "onStopTrackingTouch");
                if (MyAccessibilityService.hideRunnable != null) {
                    MyAccessibilityService.hideHandler.postDelayed(MyAccessibilityService.hideRunnable, MyAccessibilityService.timeOut);
                }
            }
        });
        seekcall.setProgress((maxProgressVal * currentCall) / maxCall);
        seekMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                int i3 = (MyAccessibilityService.maxMusic * i2) / MyAccessibilityService.maxProgressVal;
                Log.e("VVV", "Vol : " + i3);
                MyAccessibilityService.audioManager.setStreamVolume(3, i3, 0);
            }

            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (MyAccessibilityService.hideRunnable != null) {
                    MyAccessibilityService.hideHandler.removeCallbacks(MyAccessibilityService.hideRunnable);
                }
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (MyAccessibilityService.hideRunnable != null) {
                    MyAccessibilityService.hideHandler.postDelayed(MyAccessibilityService.hideRunnable, MyAccessibilityService.timeOut);
                }
            }
        });
        seekMusic.setProgress((maxProgressVal * currentMusic) / maxMusic);
        seekAlarm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                int i3 = (MyAccessibilityService.maxAlarm * i2) / MyAccessibilityService.maxProgressVal;
                Log.e("Volume", "Alarm Vol : " + i3);
                MyAccessibilityService.audioManager.setStreamVolume(4, i3, 0);
            }

            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (MyAccessibilityService.hideRunnable != null) {
                    MyAccessibilityService.hideHandler.removeCallbacks(MyAccessibilityService.hideRunnable);
                }
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (MyAccessibilityService.hideRunnable != null) {
                    MyAccessibilityService.hideHandler.postDelayed(MyAccessibilityService.hideRunnable, MyAccessibilityService.timeOut);
                }
            }
        });
        seekAlarm.setProgress((maxProgressVal * currentAlarm) / maxAlarm);
    }

    public static int getPixw(int i) {
        return (screenW * i) / 1080;
    }

    private static int getPixh(int i) {
        return (screenH * i) / 1920;
    }

    public static void destroyView() {
        if (mWindowManager == null || floatView == null) {
            Log.e("Volume", "View or manager null");
            return;
        }
        Runnable runnable = hideRunnable;
        if (runnable != null) {
            hideHandler.removeCallbacks(runnable);
        }
        try {
            mWindowManager.removeView(floatView);
        } catch (Exception e) {
            Log.e("Volume", "Remove view error" + e.getMessage());
        }
    }

    public static void hideafterTime() {
        if (laymain != null) {
            Runnable runnable = hideRunnable;
            if (runnable != null) {
                hideHandler.removeCallbacks(runnable);
            }
            laymain.setVisibility(View.VISIBLE);
            Runnable runnable2 = hideRunnable;
            if (runnable2 != null) {
                hideHandler.postDelayed(runnable2, timeOut);
            }
        }
    }

    public static void resize() {
        if (audioManager.isMusicActive()) {
            layring.setVisibility(View.GONE);
            layMusic.setVisibility(View.VISIBLE);
            return;
        }
        layring.setVisibility(View.VISIBLE);
        layMusic.setVisibility(View.GONE);
    }

    @Override 
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        new Utl(this);
        if (Utl.getonoff().booleanValue()) {
            Log.e("Volume", "access call");
            if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                Log.e("Volume", "Package Name : " + ((String) accessibilityEvent.getPackageName()));
                return;
            }
            return;
        }
        Log.e("Volume", "access call but off");
    }

    @Override 
    public void onServiceConnected() {
        mContext = this;
        screenW = getResources().getDisplayMetrics().widthPixels;
        screenH = getResources().getDisplayMetrics().heightPixels;
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        addManageView();
        resize();
        setrunnable();
        super.onServiceConnected();
    }

    private void setrunnable() {
        hideRunnable = new Runnable() { 
            @Override 
            public void run() {
                MyAccessibilityService.this.hideMainView();
                MyAccessibilityService.hasRun = false;
            }
        };
    }

    public void hideMainView() {
        if (laymain != null) {
            Log.e("Volume", "hide all view");
            laymain.setVisibility(View.GONE);
            laysub.setVisibility(View.GONE);
            btn_tune.setVisibility(View.GONE);
            btnmore.setImageResource(R.drawable.ic_baseline_navigate_before_24);
            btnmore.setTag(0);
            new Utl(mContext);
            if (Utl.pref.getBoolean("isleft", false)) {
                params.x = 0;
            } else {
                int pixw = getPixw(150);
                params.x = screenW - pixw;
            }
            View view = floatView;
            if (view != null) {
                try {
                    mWindowManager.updateViewLayout(view, params);
                } catch (Exception unused) {
                }
            }
            destroyView();
            return;
        }
        Log.e("Volume", "laymain null");
    }

    @Override 
    public boolean onKeyEvent(KeyEvent keyEvent) {
        Boolean bool = Utl.getonoff();
        new Utl(mContext);
        timeOut = Utl.pref.getLong("timeout", 3000L);
        if (!bool.booleanValue()) {
            return super.onKeyEvent(keyEvent);
        }
        Log.e("Volume", "action : " + keyEvent.getAction());
        int keyCode = keyEvent.getKeyCode();
        if (keyCode != 25 && keyCode != 24) {
            return super.onKeyEvent(keyEvent);
        }
        if (keyEvent.getAction() == 0) {
            return true;
        }
        if (!hasRun) {
            hasRun = true;
            viewCrewate();
        }
        if (hideRunnable == null) {
            setrunnable();
        }
        checkRing(audioManager.getRingerMode());
        if (audioManager.isMusicActive()) {
            int streamVolume = audioManager.getStreamVolume(3);
            currentMusic = streamVolume;
            if (keyCode == 24) {
                int i = streamVolume + 1;
                currentMusic = i;
                int i2 = maxMusic;
                if (i > i2) {
                    currentMusic = i2;
                }
            } else if (keyCode == 25) {
                int i3 = streamVolume - 1;
                currentMusic = i3;
                if (i3 < 0) {
                    currentMusic = 0;
                }
            }
            audioManager.setStreamVolume(3, currentMusic, 0);
            int i4 = cur;
            if ((Helper.check == 0 && (i4 == 0 || i4 == 1 || i4 == 2 || i4 == 3)) || ((Helper.check == 1 && (i4 == 0 || i4 == 1 || i4 == 2 || i4 == 3)) || ((Helper.check == 2 && (i4 == 0 || i4 == 1)) || (Helper.check == 3 && (i4 == 2 || i4 == 3))))) {
                vseekMusic.setProgress((maxProgressVal * currentMusic) / maxMusic);
            } else {
                seekMusic.setProgress((maxProgressVal * currentMusic) / maxMusic);
            }
        } else {
            int streamVolume2 = audioManager.getStreamVolume(2);
            currentRing = streamVolume2;
            if (keyCode == 24) {
                int i5 = streamVolume2 + 1;
                currentRing = i5;
                int i6 = maxRing;
                if (i5 > i6) {
                    currentRing = i6;
                }
            } else if (keyCode == 25) {
                int i7 = streamVolume2 - 1;
                currentRing = i7;
                if (i7 < 0) {
                    currentRing = 0;
                }
            }
            audioManager.setStreamVolume(2, currentRing, 0);
            int i8 = cur;
            if ((Helper.check == 0 && (i8 == 0 || i8 == 1 || i8 == 2 || i8 == 3)) || ((Helper.check == 1 && (i8 == 0 || i8 == 1 || i8 == 2 || i8 == 3)) || ((Helper.check == 2 && (i8 == 0 || i8 == 1)) || (Helper.check == 3 && (i8 == 2 || i8 == 3))))) {
                vseekRing.setProgress((maxProgressVal * currentRing) / maxRing);
            } else {
                seekRing.setProgress((maxProgressVal * currentRing) / maxRing);
            }
        }
        hideafterTime();
        return true;
    }

    @Override 
    public void onInterrupt() {
        Log.e("Volume", "onInterrupt");
    }

    public static void setNewRingerMode() {
        int ringerMode = audioManager.getRingerMode();
        if (ringerMode == 1) {
            audioManager.setRingerMode(2);
            btn_ringmode.setImageDrawable(mContext.getDrawable(R.drawable.ring_icon3));
            Utl.setRingMode("ring", "ring");
            vseekRing.setProgress(400);
        } else if (ringerMode != 2) {
        } else {
            audioManager.setRingerMode(1);
            btn_ringmode.setImageDrawable(mContext.getDrawable(R.drawable.ic_vibration_24dp));
            Utl.setRingMode("vibrate", "vibrate");
            vseekRing.setProgress(0);
        }
    }

    public void checkRing(int i) {
        if (i == 1) {
            btn_ringmode.setImageDrawable(mContext.getDrawable(R.drawable.ic_vibration_24dp));
        } else if (i != 2) {
        } else {
            btn_ringmode.setImageDrawable(mContext.getDrawable(R.drawable.ring_icon3));
        }
    }

    public static void ApplyStyle() {
        if (Helper.check == 0) {
            int i = Utl.pref.getInt("neon", 0);
            cur = i;
            if (i == 0) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.ngstyle1, (ViewGroup) null);
            } else if (i == 1) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.nbstyle2, (ViewGroup) null);
            } else if (i == 2) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.nrstyle3, (ViewGroup) null);
            } else if (i == 3) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.nystyle4, (ViewGroup) null);
            }
        } else if (Helper.check == 1) {
            int i2 = Utl.pref.getInt("glitter", 0);
            cur = i2;
            if (i2 == 0) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.grstyle1, (ViewGroup) null);
            } else if (i2 == 1) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.gystyle2, (ViewGroup) null);
            } else if (i2 == 2) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.gbstyle3, (ViewGroup) null);
            } else if (i2 == 3) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.gostyle4, (ViewGroup) null);
            }
        } else if (Helper.check == 2) {
            int i3 = Utl.pref.getInt("emoji", 0);
            cur = i3;
            if (i3 == 0) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.eastyle1, (ViewGroup) null);
            } else if (i3 == 1) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.ebstyle2, (ViewGroup) null);
            } else if (i3 == 2) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.esstyle3, (ViewGroup) null);
            } else if (i3 == 3) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.esstyle4, (ViewGroup) null);
            }
        } else if (Helper.check == 3) {
            int i4 = Utl.pref.getInt("general", 0);
            cur = i4;
            if (i4 == 0) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.thstyle1, (ViewGroup) null);
            } else if (i4 == 1) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.thstyle2, (ViewGroup) null);
            } else if (i4 == 2) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.thstyle4, (ViewGroup) null);
            } else if (i4 == 3) {
                floatView = LayoutInflater.from(mContext).inflate(R.layout.thstyle3, (ViewGroup) null);
            }
        }
    }
}
