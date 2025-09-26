package com.demo.volumeslider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.demo.volumeslider.adapter.StyleAdapter;
import com.demo.volumeslider.view.OnclickInterface;
import com.demo.volumeslider.view.VolumeView;


public class ThemestyleActivity extends Activity {
    private static final int REQ_PERMISSION = 10;
    private FrameLayout adContainerView;
    public StyleAdapter ad_view;
    ImageView btnmore;
    LinearLayout laymusic;
    LinearLayout layring;
    LinearLayout layseek;
    LinearLayout layselected;
    LinearLayout laysub;
    Context mContext = this;
    RecyclerView recyclerView;
    SeekBar seekVol;
    View viewSelected;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_themestyle);

        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd_Counter(this);


        new Utl(this.mContext);
        inint();
        int i = Utl.pref.getInt("general", 0);
        if (i == 0) {
            this.viewSelected = LayoutInflater.from(this.mContext).inflate(R.layout.thstyle1, (ViewGroup) null);
        } else if (i == 1) {
            this.viewSelected = LayoutInflater.from(this.mContext).inflate(R.layout.thstyle2, (ViewGroup) null);
        } else if (i == 2) {
            this.viewSelected = LayoutInflater.from(this.mContext).inflate(R.layout.thstyle4, (ViewGroup) null);
        } else if (i == 3) {
            this.viewSelected = LayoutInflater.from(this.mContext).inflate(R.layout.thstyle3, (ViewGroup) null);
        }
        this.layselected.addView(this.viewSelected);
        if (i == 2 || i == 3) {
            Utl.setLLayout(this.mContext, (VolumeView) this.viewSelected.findViewById(R.id.seekring), 94, 380);
            Utl.setLLayout(this.mContext, (VolumeView) this.viewSelected.findViewById(R.id.seekmusic), 94, 380);
            Utl.setLLayout(this.mContext, (VolumeView) this.viewSelected.findViewById(R.id.seekAlarm), 94, 380);
            Utl.setLLayout(this.mContext, (VolumeView) this.viewSelected.findViewById(R.id.seekcall), 94, 380);
        }
        this.laysub = (LinearLayout) this.viewSelected.findViewById(R.id.laysub);
        this.btnmore = (ImageView) this.viewSelected.findViewById(R.id.btnmore);
        this.layseek = (LinearLayout) this.viewSelected.findViewById(R.id.layseek);
        this.laymusic = (LinearLayout) this.viewSelected.findViewById(R.id.laymusic);
        Utl.setLSquare(this.mContext, (ImageView) this.viewSelected.findViewById(R.id.btnring), 50);
        Utl.setLSquare(this.mContext, (ImageView) this.viewSelected.findViewById(R.id.btnmusic), 50);
        Utl.setLSquare(this.mContext, (ImageView) this.viewSelected.findViewById(R.id.btnalarm), 50);
        Utl.setLSquare(this.mContext, (ImageView) this.viewSelected.findViewById(R.id.btncall), 50);
        Utl.setLSquare(this.mContext, this.btnmore, 72);
        this.btnmore.setTag(0);
        if (((Integer) this.btnmore.getTag()).intValue() == 0) {
            this.laysub.setVisibility(View.GONE);
            this.btnmore.setImageResource(R.drawable.ic_baseline_navigate_before_24);
        } else {
            this.laysub.setVisibility(View.VISIBLE);
            this.laymusic.setVisibility(View.VISIBLE);
            this.layring.setVisibility(View.VISIBLE);
            this.btnmore.setImageResource(R.drawable.ic_baseline_navigate_next_24);
        }
        this.btnmore.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                int i2 = ((Integer) view.getTag()).intValue() == 0 ? 1 : 0;
                ThemestyleActivity.this.btnmore.setTag(Integer.valueOf(i2));
                if (i2 == 0) {
                    ThemestyleActivity.this.laysub.setVisibility(View.GONE);
                    ThemestyleActivity.this.btnmore.setImageResource(R.drawable.ic_baseline_navigate_before_24);
                    return;
                }
                ThemestyleActivity.this.layring.setVisibility(View.VISIBLE);
                ThemestyleActivity.this.laymusic.setVisibility(View.VISIBLE);
                ThemestyleActivity.this.laysub.setVisibility(View.VISIBLE);
                ThemestyleActivity.this.btnmore.setImageResource(R.drawable.ic_baseline_navigate_next_24);
            }
        });
        ViewGroup viewGroup = null;
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.thstyle1, viewGroup);
        View inflate2 = LayoutInflater.from(this.mContext).inflate(R.layout.thstyle2, viewGroup);
        View inflate3 = LayoutInflater.from(this.mContext).inflate(R.layout.thstyle4, viewGroup);
        View inflate4 = LayoutInflater.from(this.mContext).inflate(R.layout.thstyle3, viewGroup);
        ArrayList arrayList = new ArrayList();
        arrayList.add(inflate);
        arrayList.add(inflate2);
        arrayList.add(inflate3);
        arrayList.add(inflate4);
        StyleAdapter styleAdapter = new StyleAdapter(this.mContext, i, arrayList, new OnclickInterface() { 
            @Override 
            public void OnMyClick2(int i2, Object obj) {
            }

            @Override 
            public void OnMyClick1(int i2, Object obj) {
                View view = null;
                if (i2 == 0) {
                    view = LayoutInflater.from(ThemestyleActivity.this).inflate(R.layout.thstyle1, (ViewGroup) null);
                } else if (i2 == 1) {
                    view = LayoutInflater.from(ThemestyleActivity.this).inflate(R.layout.thstyle2, (ViewGroup) null);
                } else if (i2 == 2) {
                    view = LayoutInflater.from(ThemestyleActivity.this).inflate(R.layout.thstyle4, (ViewGroup) null);
                } else if (i2 == 3) {
                    view = LayoutInflater.from(ThemestyleActivity.this).inflate(R.layout.thstyle3, (ViewGroup) null);
                }
                ThemestyleActivity.this.setselectedView(i2, view, false);
                ThemestyleActivity.this.ad_view.setselected(i2);
                Utl.editor.putInt("general", i2);
                Utl.editor.commit();
            }
        });
        this.ad_view = styleAdapter;
        this.recyclerView.setAdapter(styleAdapter);
        this.seekVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                Log.e("Volume", "demo seek : " + i2);
            }

            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("Volume", "demo onStartTrackingTouch");
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("Volume", "demo onStopTrackingTouch");
            }
        });
    }

    public void setselectedView(int i, View view, Boolean bool) {
        if (!bool.booleanValue()) {
            this.layselected.removeView(this.viewSelected);
        }
        try {
            this.layselected.addView(view);
            this.viewSelected = view;
        } catch (Exception e) {
            Log.e("Volume", "error add view : " + e.getMessage());
        }
        this.layseek = (LinearLayout) this.viewSelected.findViewById(R.id.layseek);
        this.laysub = (LinearLayout) this.viewSelected.findViewById(R.id.laysub);
        this.btnmore = (ImageView) this.viewSelected.findViewById(R.id.btnmore);
        Utl.setLSquare(this.mContext, (ImageView) this.viewSelected.findViewById(R.id.btnring), 50);
        Utl.setLSquare(this.mContext, (ImageView) this.viewSelected.findViewById(R.id.btnmusic), 50);
        Utl.setLSquare(this.mContext, (ImageView) this.viewSelected.findViewById(R.id.btnalarm), 50);
        Utl.setLSquare(this.mContext, (ImageView) this.viewSelected.findViewById(R.id.btncall), 50);
        if (i == 2 || i == 3) {
            VolumeView volumeView = (VolumeView) this.viewSelected.findViewById(R.id.seekring);
            VolumeView volumeView2 = (VolumeView) this.viewSelected.findViewById(R.id.seekmusic);
            VolumeView volumeView3 = (VolumeView) this.viewSelected.findViewById(R.id.seekAlarm);
            VolumeView volumeView4 = (VolumeView) this.viewSelected.findViewById(R.id.seekcall);
            Utl.setLLayout(this.mContext, volumeView2, 94, 380);
            Utl.setLLayout(this.mContext, volumeView3, 94, 380);
            Utl.setLLayout(this.mContext, volumeView4, 94, 380);
            volumeView.setMax(380);
            volumeView2.setMax(380);
            volumeView3.setMax(380);
            volumeView4.setMax(380);
            volumeView.setProgress(50);
            volumeView2.setProgress(50);
            volumeView3.setProgress(50);
            volumeView4.setProgress(50);
        }
        Utl.setLSquare(this.mContext, this.btnmore, 72);
        this.btnmore.setTag(0);
        if (((Integer) this.btnmore.getTag()).intValue() == 0) {
            this.laysub.setVisibility(View.GONE);
        } else {
            this.laysub.setVisibility(View.VISIBLE);
        }
        this.btnmore.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view2) {
                int i2 = ((Integer) view2.getTag()).intValue() == 0 ? 1 : 0;
                ThemestyleActivity.this.btnmore.setTag(Integer.valueOf(i2));
                if (i2 == 0) {
                    ThemestyleActivity.this.laysub.setVisibility(View.GONE);
                    ThemestyleActivity.this.btnmore.setImageResource(R.drawable.ic_baseline_navigate_before_24);
                    return;
                }
                ThemestyleActivity.this.laysub.setVisibility(View.VISIBLE);
                ThemestyleActivity.this.layring.setVisibility(View.VISIBLE);
                ThemestyleActivity.this.laymusic.setVisibility(View.VISIBLE);
                ThemestyleActivity.this.btnmore.setImageResource(R.drawable.ic_baseline_navigate_next_24);
            }
        });
    }

    private void inint() {
        this.seekVol = (SeekBar) findViewById(R.id.seekvol);
        this.layselected = (LinearLayout) findViewById(R.id.layselected);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.VERTICAL, false));
    }

    @Override 
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }
}
