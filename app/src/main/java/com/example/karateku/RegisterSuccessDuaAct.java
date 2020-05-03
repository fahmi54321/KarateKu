package com.example.karateku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterSuccessDuaAct extends AppCompatActivity {

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    Button bttnTambah,bttnKeluar,bttnView;
    de.hdodenhof.circleimageview.CircleImageView imgLogo;
    TextView txtJudul;
    Animation top_to_bottom,bottom_to_top,scale_animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success_dua);

        getUsernameLocal();

        top_to_bottom= AnimationUtils.loadAnimation(this,R.anim.top_to_bottom);
        bottom_to_top= AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);
        scale_animation= AnimationUtils.loadAnimation(this,R.anim.scale_animation);

        bttnTambah = findViewById(R.id.bttnTambah);
        imgLogo = findViewById(R.id.imgLogo);
        txtJudul = findViewById(R.id.txtJudul);
        bttnKeluar = findViewById(R.id.bttnKeluar);
        bttnView = findViewById(R.id.bttnView);

        imgLogo.startAnimation(scale_animation);
        txtJudul.startAnimation(top_to_bottom);

        bttnTambah.startAnimation(bottom_to_top);
        bttnKeluar.startAnimation(bottom_to_top);
        bttnView.startAnimation(bottom_to_top);

        bttnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterSuccessDuaAct.this,NewAccountTigaAct.class);
                startActivity(intent);
                finish();
            }
        });
        bttnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterSuccessDuaAct.this,DetailsPesertaAct.class);
                startActivity(intent);
            }
        });

        bttnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterSuccessDuaAct.this,SigninAct.class);
                startActivity(intent);
                finish();
            }
        });



    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
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
            startActivity(new Intent(RegisterSuccessDuaAct.this, TentangAct.class));
        }
        return true;
    }
}
