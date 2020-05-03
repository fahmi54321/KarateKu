package com.example.karateku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GetStartedAct extends AppCompatActivity {

    Animation top_to_bottom,bottom_to_top;
    Button bttnNewAccount,bttnSignin;
    ImageView ImgPnp;
    TextView txtJudul,txtJudul1,txtJudul2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        top_to_bottom= AnimationUtils.loadAnimation(this,R.anim.top_to_bottom);
        bottom_to_top= AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);

        bttnNewAccount = findViewById(R.id.bttnNewAccount);
        bttnSignin = findViewById(R.id.bttnSignin);
        ImgPnp = findViewById(R.id.ImgPnp);
        txtJudul = findViewById(R.id.txtJudul);
        txtJudul1 = findViewById(R.id.txtJudul1);
        txtJudul2 = findViewById(R.id.txtJudul2);

        ImgPnp.startAnimation(top_to_bottom);
        txtJudul.startAnimation(top_to_bottom);
        txtJudul1.startAnimation(top_to_bottom);
        txtJudul2.startAnimation(top_to_bottom);
        bttnSignin.startAnimation(bottom_to_top);
        bttnNewAccount.startAnimation(bottom_to_top);

        bttnNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonewaccount = new Intent(GetStartedAct.this,NewAccountSatuAct.class);
                startActivity(gotonewaccount);
            }
        });

        bttnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonewaccount = new Intent(GetStartedAct.this,SigninAct.class);
                startActivity(gotonewaccount);
            }
        });

    }
}
