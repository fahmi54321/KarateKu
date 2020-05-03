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

public class LoginSuccessAct extends AppCompatActivity {

    Button bttnFormUmum,bttnFormPeserta,bttnKeluar,bttnView;
    Animation top_to_bottom,bottom_to_top,scale_animation;
    de.hdodenhof.circleimageview.CircleImageView imgLogo;
    TextView txtJudul,txtKata;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        getUsernameLocal();

        top_to_bottom= AnimationUtils.loadAnimation(this,R.anim.top_to_bottom);
        bottom_to_top= AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);
        scale_animation= AnimationUtils.loadAnimation(this,R.anim.scale_animation);

        imgLogo = findViewById(R.id.imgLogo);
        txtJudul = findViewById(R.id.txtJudul);
        txtKata = findViewById(R.id.txtKata);

        bttnFormPeserta = findViewById(R.id.bttnFormPeserta);
        bttnFormUmum = findViewById(R.id.bttnFormUmum);
        bttnKeluar = findViewById(R.id.bttnKeluar);
        bttnView = findViewById(R.id.bttnView);

        imgLogo.startAnimation(scale_animation);
        txtJudul.startAnimation(top_to_bottom);
        txtKata.startAnimation(top_to_bottom);

        bttnFormPeserta.startAnimation(bottom_to_top);
        bttnFormUmum.startAnimation(bottom_to_top);
        bttnKeluar.startAnimation(bottom_to_top);
        bttnView.startAnimation(bottom_to_top);



        bttnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoviewpeserta = new Intent(LoginSuccessAct.this,DetailsPesertaAct.class);
                startActivity(gotoviewpeserta);
            }
        });

        bttnFormUmum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoformumum = new Intent(LoginSuccessAct.this,NewAccountDuaAct.class);
                startActivity(gotoformumum);
            }
        });

        bttnFormPeserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoformpeserta = new Intent(LoginSuccessAct.this,NewAccountTigaAct.class);
                startActivity(gotoformpeserta);
            }
        });

        bttnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key,null);
                editor.apply();

                Intent gotoformpeserta = new Intent(LoginSuccessAct.this,SigninAct.class);
                startActivity(gotoformpeserta);
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
            startActivity(new Intent(LoginSuccessAct.this, TentangAct.class));
        }
        return true;
    }
}
