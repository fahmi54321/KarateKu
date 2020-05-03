package com.example.karateku;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterSuccessAct extends AppCompatActivity {

    Button bttnExplore;
    Animation top_to_bottom,bottom_to_top,scale_animation;
    de.hdodenhof.circleimageview.CircleImageView imgLogo;
    TextView txtJudul,txtKata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);

        top_to_bottom= AnimationUtils.loadAnimation(this,R.anim.top_to_bottom);
        bottom_to_top= AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);
        scale_animation= AnimationUtils.loadAnimation(this,R.anim.scale_animation);

        bttnExplore = findViewById(R.id.bttnExplore);
        imgLogo = findViewById(R.id.imgLogo);
        txtJudul = findViewById(R.id.txtJudul);
        txtKata = findViewById(R.id.txtKata);

        imgLogo.startAnimation(scale_animation);
        txtJudul.startAnimation(top_to_bottom);
        txtKata.startAnimation(top_to_bottom);

        bttnExplore.startAnimation(bottom_to_top);

        bttnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosignin = new Intent(RegisterSuccessAct.this,SigninAct.class);
                startActivity(gotosignin);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.tentang)
        {
            startActivity(new Intent(RegisterSuccessAct.this, TentangAct.class));
        }
        return true;
    }
}
