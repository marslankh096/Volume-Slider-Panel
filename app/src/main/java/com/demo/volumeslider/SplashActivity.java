package com.demo.volumeslider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    int[] imageArray = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4};
    ImageView logo;
    ImageView proimg;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);
        this.proimg = (ImageView) findViewById(R.id.txtlogo);
        this.logo = (ImageView) findViewById(R.id.logo1);
        new Handler();
        new Handler().postDelayed(new Runnable() { 
            @Override 
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 3000L);
    }

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
