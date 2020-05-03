package com.example.karateku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.karateku.R.drawable.bttn_input_error;

public class NewAccountSatuAct extends AppCompatActivity {

    Button bttnContinue;
    LinearLayout bttnBack;
    EditText username,password,email_address,nama_lengkap;

    //Tahap 1
    DatabaseReference reference;

    //Tahap 2 Untuk menjadi username menjadi patokan untuk data selanjutnya
    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_satu);

        bttnBack = findViewById(R.id.bttnBack);
        bttnContinue = findViewById(R.id.bttnContinue);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);
        nama_lengkap = findViewById(R.id.nama_lengkap);

        bttnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotogetstarted = new Intent(NewAccountSatuAct.this,GetStartedAct.class);
                startActivity(gotogetstarted);
            }
        });

        bttnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bttnContinue.setText("LOADING...");
                bttnContinue.setEnabled(false);

                if(nama_lengkap.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Nama Lengkap Kosong", Toast.LENGTH_SHORT).show();
                    nama_lengkap.setBackgroundResource(bttn_input_error);
                    nama_lengkap.requestFocus();
                    bttnContinue.setText("SIGN IN");
                    bttnContinue.setEnabled(true);
                }
                else if (username.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Username Kosong", Toast.LENGTH_SHORT).show();
                    username.setBackgroundResource(bttn_input_error);
                    username.requestFocus();
                    bttnContinue.setText("SIGN IN");
                    bttnContinue.setEnabled(true);
                }
                else if(password.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Password Kosong", Toast.LENGTH_SHORT).show();
                    password.setBackgroundResource(bttn_input_error);
                    password.requestFocus();
                    bttnContinue.setText("SIGN IN");
                    bttnContinue.setEnabled(true);
                }
                else if (email_address.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Email Address Kosong", Toast.LENGTH_SHORT).show();
                    email_address.setBackgroundResource(bttn_input_error);
                    email_address.requestFocus();
                    bttnContinue.setText("SIGN IN");
                    bttnContinue.setEnabled(true);
                }
                else
                {
                    //Tahap 3 menyimpan Ke Penyimpanan Local Handphone
                    SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(username_key,username.getText().toString());
                    editor.apply();

//                 Tahap 4 Test apakah username sudah tersimpan di lokal
//                Toast.makeText(getApplicationContext(),"Username : "+username.getText().toString(), Toast.LENGTH_SHORT).show();

                    // Tahap 5 menyimpan ke firebase
                    reference = FirebaseDatabase.getInstance().getReference().
                            child("Users").child(username.getText().toString()); // Menyimpan data sesuai username yang terletak di Tabel Users
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataSnapshot.getRef().child("username").setValue(username.getText().toString()); // data username ke firebase
                            dataSnapshot.getRef().child("password").setValue(password.getText().toString()); // data password ke firebase
                            dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString()); // data email_address ke firebase
                            dataSnapshot.getRef().child("nama_lengkap").setValue(nama_lengkap.getText().toString()); // data email_address ke firebase

                            Toast.makeText(getApplicationContext(), "Data Sudah Masuk", Toast.LENGTH_SHORT).show();

                            Intent gotonewaccountdua = new Intent(NewAccountSatuAct.this,RegisterSuccessAct.class);
                            startActivity(gotonewaccountdua);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        });
    }
}
