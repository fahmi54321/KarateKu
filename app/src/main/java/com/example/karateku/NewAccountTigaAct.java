package com.example.karateku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.karateku.R.drawable.bttn_input_error;

public class NewAccountTigaAct extends AppCompatActivity {

    EditText nama_atlet,ttl,kontingen;
    Button bttnUploadKartu,Tambah,bttnContinue,bttnSave,bttnView;
    LinearLayout bttnBack;
    ImageView PhotoKartu;

    //Tahap 1 Menentukan variabel untuk menentukan lokasi foto serta variabel untuk maksimal photo untuk input
    Uri photo_location;
    Integer photo_max=1;

    //Tahap 6
    DatabaseReference reference,ref_username;
    StorageReference storage;

    //Tahap 7
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_tiga);

        //Tahap 9
        getUsernameLocal();


        nama_atlet = findViewById(R.id.nama_atlet);
        ttl = findViewById(R.id.ttl);
        kontingen = findViewById(R.id.kontingen);
        bttnContinue = findViewById(R.id.bttnContinue);
        bttnSave = findViewById(R.id.bttnSave);
        bttnBack = findViewById(R.id.bttnBack);
        bttnView = findViewById(R.id.bttnView);


        bttnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotologinsuccess = new Intent(NewAccountTigaAct.this,LoginSuccessAct.class);
                startActivity(gotologinsuccess);
            }
        });

        bttnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotologinsuccess = new Intent(NewAccountTigaAct.this,DetailsPesertaAct.class);
                startActivity(gotologinsuccess);
            }
        });


        bttnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tahap 10
                reference = FirebaseDatabase.getInstance().getReference().child("Peserta").child(nama_atlet.getText().toString());


                bttnSave.setText("LOADING...");
                bttnSave.setEnabled(false);

                if(nama_atlet.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Nama Atlet Kosong", Toast.LENGTH_SHORT).show();
                    nama_atlet.setBackgroundResource(bttn_input_error);
                    nama_atlet.requestFocus();
                    bttnSave.setText("SIGN IN");
                    bttnSave.setEnabled(true);
                }
                else if (ttl.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "TTL Kosong", Toast.LENGTH_SHORT).show();
                    ttl.setBackgroundResource(bttn_input_error);
                    ttl.requestFocus();
                    bttnSave.setText("SIGN IN");
                    bttnSave.setEnabled(true);
                }
                else
                {
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            reference.getRef().child("nama_atlet").setValue(nama_atlet.getText().toString());
                            reference.getRef().child("ttl").setValue(ttl.getText().toString());
                            reference.getRef().child("kontingen").setValue(kontingen.getText().toString());
                            reference.getRef().child("url_photo_atlet").setValue("https://firebasestorage.googleapis.com/v0/b/karateku-e5bb6.appspot.com/o/PhotoKosong%2Ficon_nopic.png?alt=media&token=b06b6cfa-ae66-4c94-89cd-b79fb5c6ef72");
                            reference.getRef().child("url_scan_ktp").setValue("https://firebasestorage.googleapis.com/v0/b/karateku-e5bb6.appspot.com/o/PhotoKosong%2Ficon_nopic.png?alt=media&token=b06b6cfa-ae66-4c94-89cd-b79fb5c6ef72");
                            reference.getRef().child("url_scan_kk").setValue("https://firebasestorage.googleapis.com/v0/b/karateku-e5bb6.appspot.com/o/PhotoKosong%2Ficon_nopic.png?alt=media&token=b06b6cfa-ae66-4c94-89cd-b79fb5c6ef72");
                            reference.getRef().child("url_scan_kitas").setValue("https://firebasestorage.googleapis.com/v0/b/karateku-e5bb6.appspot.com/o/PhotoKosong%2Ficon_nopic.png?alt=media&token=b06b6cfa-ae66-4c94-89cd-b79fb5c6ef72");
                            reference.getRef().child("url_scan_passport").setValue("https://firebasestorage.googleapis.com/v0/b/karateku-e5bb6.appspot.com/o/PhotoKosong%2Ficon_nopic.png?alt=media&token=b06b6cfa-ae66-4c94-89cd-b79fb5c6ef72");

                            Toast.makeText(getApplicationContext(), "Data Berhasil Masuk", Toast.LENGTH_SHORT).show();
                            Intent gotouploadscan = new Intent(NewAccountTigaAct.this,NewAccountEmpatAct.class);
                            gotouploadscan.putExtra("atlet",nama_atlet.getText().toString());

                            startActivity(gotouploadscan);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                    ref_username = FirebaseDatabase.getInstance().getReference().child("Peserta").child(username_key_new).child("username");
//
//                    ref_username.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            ref_username.getRef().setValue(username_key_new);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
                }
            }
        });

    }

    //Tahap 8 Fungsi untuk mendapatkan username local
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
            startActivity(new Intent(NewAccountTigaAct.this, TentangAct.class));
        }
        return true;
    }
}
