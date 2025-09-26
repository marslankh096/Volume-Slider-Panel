package com.demo.volumeslider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.volumeslider.view.Helper;


public class ThemePacks extends AppCompatActivity {
    ImageView img_emoji;
    ImageView img_gerneral;
    ImageView img_glitter;
    ImageView img_neon;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_theme_packs);

        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd_Counter(this);


        this.img_gerneral = (ImageView) findViewById(R.id.general);
        this.img_glitter = (ImageView) findViewById(R.id.glitter);
        this.img_neon = (ImageView) findViewById(R.id.neon);
        this.img_emoji = (ImageView) findViewById(R.id.emoji);
        this.img_neon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Helper.check = 0;
                ThemePacks.this.startActivity(new Intent(ThemePacks.this, NeonThemeActivity.class));
            }
        });
        this.img_glitter.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Helper.check = 1;
                ThemePacks.this.startActivity(new Intent(ThemePacks.this, GlitterThemeActivity.class));
            }
        });
        this.img_emoji.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Helper.check = 2;
                ThemePacks.this.startActivity(new Intent(ThemePacks.this, EmojiThemeActivity.class));
            }
        });
        this.img_gerneral.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Helper.check = 3;
                ThemePacks.this.startActivity(new Intent(ThemePacks.this, ThemestyleActivity.class));
            }
        });
    }
}
