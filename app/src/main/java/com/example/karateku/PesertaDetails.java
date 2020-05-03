package com.example.karateku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PesertaDetails extends AppCompatActivity {


    TextView xnama_atlet,xttl;
    ImageView Img_scan_ktp,Img_scan_kk,Img_scan_kitas,Img_scan_passport,Img_photo_atlet;
    DatabaseReference reference;
    Button bttnHome,bttnEdit;
    LinearLayout bttnBack;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peserta_details);

        getUsernameLocal();

        Bundle bundle = getIntent().getExtras();
        final String nama_atlet_baru=bundle.getString("nama_atlet");

        xnama_atlet = findViewById(R.id.xnama_atlet);
        xttl = findViewById(R.id.xttl);
        Img_scan_kk = findViewById(R.id.Img_scan_kk);
        Img_scan_ktp = findViewById(R.id.Img_scan_ktp);
        Img_scan_passport = findViewById(R.id.Img_scan_passport);
        Img_scan_kitas = findViewById(R.id.Img_scan_kitas);
        Img_photo_atlet = findViewById(R.id.Img_photo_atlet);
        bttnHome = findViewById(R.id.bttnHome);
        bttnBack = findViewById(R.id.bttnBack);
        bttnEdit = findViewById(R.id.bttnEdit);

        bttnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PesertaDetails.this,EditPesertaAct.class);
                intent.putExtra("kiriman",nama_atlet_baru);
                startActivity(intent);
            }
        });

        bttnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PesertaDetails.this,DetailsPesertaAct.class);
                startActivity(intent);
                finish();
            }
        });

        bttnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PesertaDetails.this,LoginSuccessAct.class);
                startActivity(intent);
                finish();
            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("Peserta").child(username_key_new).child(nama_atlet_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xnama_atlet.setText(dataSnapshot.child("nama_atlet").getValue().toString());
                xttl.setText(dataSnapshot.child("ttl").getValue().toString());


                Picasso.with(PesertaDetails.this)
                        .load(dataSnapshot.child("url_photo_atlet")
                                .getValue().toString()).centerCrop()
                        .fit().into(Img_photo_atlet);

                Picasso.with(PesertaDetails.this)
                        .load(dataSnapshot.child("url_scan_ktp")
                                .getValue().toString()).centerCrop()
                        .fit().into(Img_scan_ktp);

                Picasso.with(PesertaDetails.this)
                        .load(dataSnapshot.child("url_scan_kk")
                                .getValue().toString()).centerCrop()
                        .fit().into(Img_scan_kk);

                Picasso.with(PesertaDetails.this)
                        .load(dataSnapshot.child("url_scan_kitas")
                                .getValue().toString()).centerCrop()
                        .fit().into(Img_scan_kitas);

                Picasso.with(PesertaDetails.this)
                        .load(dataSnapshot.child("url_scan_passport")
                                .getValue().toString()).centerCrop()
                        .fit().into(Img_scan_passport);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
            startActivity(new Intent(PesertaDetails.this, TentangAct.class));
        }
        return true;
    }
}
