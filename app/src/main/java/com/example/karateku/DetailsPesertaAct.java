package com.example.karateku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailsPesertaAct extends AppCompatActivity {

    LinearLayout PesertaDetails,LinearHome;
    TextView nama_atlet,ttl;
    ImageView PhotoAtlet;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    RecyclerView mypeserta_place;
    PesertaAdapter pesertaAdapter;
    ArrayList<MyPeserta> list;

    DatabaseReference reference,reference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_peserta);

        getUsernameLocal();

        PesertaDetails = findViewById(R.id.PesertaDetails);
        nama_atlet = findViewById(R.id.nama_atlet);
        ttl = findViewById(R.id.ttl);
        mypeserta_place = findViewById(R.id.mypeserta_place);
        LinearHome = findViewById(R.id.LinearHome);

        LinearHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsPesertaAct.this,LoginSuccessAct.class);
                startActivity(intent);
                finish();
            }
        });

        mypeserta_place.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyPeserta>();


        reference2 = FirebaseDatabase.getInstance().getReference().child("Peserta").child(username_key_new).child("nama_atlet");
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    MyPeserta p = dataSnapshot1.getValue(MyPeserta.class);
                    list.add(p);
                }
                //replace
                pesertaAdapter = new PesertaAdapter(DetailsPesertaAct.this,list);

                // setting parent
                mypeserta_place.setAdapter(pesertaAdapter);
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
            startActivity(new Intent(DetailsPesertaAct.this, TentangAct.class));
        }
        return true;
    }
}
