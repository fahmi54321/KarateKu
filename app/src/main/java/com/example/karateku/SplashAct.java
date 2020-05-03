package com.example.karateku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashAct extends AppCompatActivity {


    Animation scale_animation,bottom_to_top;
    ImageView ImgPnp;
    TextView txtJudul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        scale_animation= AnimationUtils.loadAnimation(this,R.anim.scale_animation);
        bottom_to_top= AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);

        ImgPnp=findViewById(R.id.ImgPnp);
        txtJudul=findViewById(R.id.txtJudul);

        ImgPnp.startAnimation(scale_animation);
        txtJudul.startAnimation(bottom_to_top);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gotogetstarted = new Intent(SplashAct.this, GetStartedAct.class);
                startActivity(gotogetstarted);
                finish();
            }
        },5000);
    }
}
