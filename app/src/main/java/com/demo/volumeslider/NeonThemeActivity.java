package com.demo.volumeslider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.demo.volumeslider.adapter.NeonThemeAdapter;
import com.demo.volumeslider.view.OnclickInterface;
import com.demo.volumeslider.view.VolumeView;


public class NeonThemeActivity extends AppCompatActivity {
    ImageView btnmore;
    LinearLayout laymusic;
    LinearLayout layring;
    LinearLayout layseek;
    LinearLayout layselected;
    LinearLayout laysub;
    public NeonThemeAdapter neonThemeAdapter;
    RecyclerView recyclerView;
    View viewSelected;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(1024);
        setContentView(R.layout.activity_neon_theme);


        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd_Counter(this);

        new Utl(this);
        init();
        int i = Utl.pref.getInt("neon", 0);
        if (i == 0) {
            this.viewSelected = LayoutInflater.from(this).inflate(R.layout.ngstyle1, (ViewGroup) null);
        } else if (i == 1) {
            this.viewSelected = LayoutInflater.from(this).inflate(R.layout.nbstyle2, (ViewGroup) null);
        } else if (i == 2) {
            this.viewSelected = LayoutInflater.from(this).inflate(R.layout.nrstyle3, (ViewGroup) null);
        } else if (i == 3) {
            this.viewSelected = LayoutInflater.from(this).inflate(R.layout.nystyle4, (ViewGroup) null);
        }
        this.layselected.addView(this.viewSelected);
        this.laysub = (LinearLayout) this.viewSelected.findViewById(R.id.laysub);
        this.btnmore = (ImageView) this.viewSelected.findViewById(R.id.btnmore);
        this.layseek = (LinearLayout) this.viewSelected.findViewById(R.id.layseek);
        this.laymusic = (LinearLayout) this.viewSelected.findViewById(R.id.laymusic);
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
                NeonThemeActivity.this.btnmore.setTag(Integer.valueOf(i2));
                if (i2 == 0) {
                    NeonThemeActivity.this.laysub.setVisibility(View.GONE);
                    NeonThemeActivity.this.btnmore.setImageResource(R.drawable.ic_baseline_navigate_before_24);
                    return;
                }
                NeonThemeActivity.this.layring.setVisibility(View.VISIBLE);
                NeonThemeActivity.this.laymusic.setVisibility(View.VISIBLE);
                NeonThemeActivity.this.laysub.setVisibility(View.VISIBLE);
                NeonThemeActivity.this.btnmore.setImageResource(R.drawable.ic_baseline_navigate_next_24);
            }
        });
        ViewGroup viewGroup = null;
        View inflate = LayoutInflater.from(this).inflate(R.layout.ngstyle1, viewGroup);
        View inflate2 = LayoutInflater.from(this).inflate(R.layout.nbstyle2, viewGroup);
        View inflate3 = LayoutInflater.from(this).inflate(R.layout.nrstyle3, viewGroup);
        View inflate4 = LayoutInflater.from(this).inflate(R.layout.nystyle4, viewGroup);
        ArrayList arrayList = new ArrayList();
        arrayList.add(inflate);
        arrayList.add(inflate2);
        arrayList.add(inflate3);
        arrayList.add(inflate4);
        NeonThemeAdapter neonThemeAdapter = new NeonThemeAdapter(this, i, arrayList, new OnclickInterface() { 
            @Override 
            public void OnMyClick2(int i2, Object obj) {
            }

            @Override 
            public void OnMyClick1(int i2, Object obj) {
                View view = null;
                if (i2 == 0) {
                    view = LayoutInflater.from(NeonThemeActivity.this).inflate(R.layout.ngstyle1, (ViewGroup) null);
                } else if (i2 == 1) {
                    view = LayoutInflater.from(NeonThemeActivity.this).inflate(R.layout.nbstyle2, (ViewGroup) null);
                } else if (i2 == 2) {
                    view = LayoutInflater.from(NeonThemeActivity.this).inflate(R.layout.nrstyle3, (ViewGroup) null);
                } else if (i2 == 3) {
                    view = LayoutInflater.from(NeonThemeActivity.this).inflate(R.layout.nystyle4, (ViewGroup) null);
                }
                NeonThemeActivity.this.setselectedView(i2, view, false);
                NeonThemeActivity.this.neonThemeAdapter.setselected(i2);
                Utl.editor.putInt("neon", i2);
                Utl.editor.commit();
            }
        });
        this.neonThemeAdapter = neonThemeAdapter;
        this.recyclerView.setAdapter(neonThemeAdapter);
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
        if (i == 1 || i == 2 || i == 5) {
            VolumeView volumeView = (VolumeView) this.viewSelected.findViewById(R.id.seekring);
            VolumeView volumeView2 = (VolumeView) this.viewSelected.findViewById(R.id.seekmusic);
            VolumeView volumeView3 = (VolumeView) this.viewSelected.findViewById(R.id.seekAlarm);
            VolumeView volumeView4 = (VolumeView) this.viewSelected.findViewById(R.id.seekcall);
            volumeView.setMax(380);
            volumeView2.setMax(380);
            volumeView3.setMax(380);
            volumeView4.setMax(380);
            volumeView.setProgress(50);
            volumeView2.setProgress(50);
            volumeView3.setProgress(50);
            volumeView4.setProgress(50);
        }
        Utl.setLSquare(this, this.btnmore, 72);
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
                NeonThemeActivity.this.btnmore.setTag(Integer.valueOf(i2));
                if (i2 == 0) {
                    NeonThemeActivity.this.laysub.setVisibility(View.GONE);
                    NeonThemeActivity.this.btnmore.setImageResource(R.drawable.ic_baseline_navigate_before_24);
                    return;
                }
                NeonThemeActivity.this.laysub.setVisibility(View.VISIBLE);
                NeonThemeActivity.this.layring.setVisibility(View.VISIBLE);
                NeonThemeActivity.this.laymusic.setVisibility(View.VISIBLE);
                NeonThemeActivity.this.btnmore.setImageResource(R.drawable.ic_baseline_navigate_next_24);
            }
        });
    }

    private void init() {
        this.layselected = (LinearLayout) findViewById(R.id.layselected);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }
}
