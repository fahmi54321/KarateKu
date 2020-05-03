package com.example.karateku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class NewAccountDuaAct extends AppCompatActivity {

    Button bttnContinue;
    LinearLayout bttnBack;
    EditText kontingen,manager,nohpmanager,official,nohpofficial,pelatih,nohppelatih,jumlahkelas;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_dua);

        getUsernameLocal();

        bttnBack = findViewById(R.id.bttnBack);
        bttnContinue = findViewById(R.id.bttnContinue);

        kontingen = findViewById(R.id.kontingen);
        manager = findViewById(R.id.manager);
        nohpmanager = findViewById(R.id.nohpmanager);
        official = findViewById(R.id.official);
        pelatih = findViewById(R.id.pelatih);
        nohpofficial = findViewById(R.id.nohpofficial);
        nohppelatih = findViewById(R.id.nohppelatih);
        jumlahkelas = findViewById(R.id.jumlahkelas);

        bttnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonewaccountsatu = new Intent(NewAccountDuaAct.this,LoginSuccessAct.class);
                startActivity(gotonewaccountsatu);
            }
        });

        bttnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bttnContinue.setText("LOADING...");
                bttnContinue.setEnabled(false);

                if(kontingen.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Kontingen Kosong", Toast.LENGTH_SHORT).show();
                    kontingen.setBackgroundResource(bttn_input_error);
                    kontingen.requestFocus();
                    bttnContinue.setText("SIGN IN");
                    bttnContinue.setEnabled(true);
                }
                else if (manager.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Manager Kosong", Toast.LENGTH_SHORT).show();
                    manager.setBackgroundResource(bttn_input_error);
                    manager.requestFocus();
                    bttnContinue.setText("SIGN IN");
                    bttnContinue.setEnabled(true);
                }
                else if(nohpmanager.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "No Hp Manager Kosong", Toast.LENGTH_SHORT).show();
                    nohpmanager.setBackgroundResource(bttn_input_error);
                    nohpmanager.requestFocus();
                    bttnContinue.setText("SIGN IN");
                    bttnContinue.setEnabled(true);
                }
                else if (official.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Official Kosong", Toast.LENGTH_SHORT).show();
                    official.setBackgroundResource(bttn_input_error);
                    official.requestFocus();
                    bttnContinue.setText("SIGN IN");
                    bttnContinue.setEnabled(true);
                }
                else if (nohpofficial.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "No Hp Official", Toast.LENGTH_SHORT).show();
                    nohpofficial.setBackgroundResource(bttn_input_error);
                    nohpofficial.requestFocus();
                    bttnContinue.setText("SIGN IN");
                    bttnContinue.setEnabled(true);
                }
                else if(pelatih.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Pelatih Kosong", Toast.LENGTH_SHORT).show();
                    pelatih.setBackgroundResource(bttn_input_error);
                    pelatih.requestFocus();
                    bttnContinue.setText("SIGN IN");
                    bttnContinue.setEnabled(true);
                }
                else if (nohppelatih.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "No Hp Pelatih Kosong", Toast.LENGTH_SHORT).show();
                    nohppelatih.setBackgroundResource(bttn_input_error);
                    nohppelatih.requestFocus();
                    bttnContinue.setText("SIGN IN");
                    bttnContinue.setEnabled(true);
                }
                else if (jumlahkelas.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Jumlah Kelas Kosong", Toast.LENGTH_SHORT).show();
                    jumlahkelas.setBackgroundResource(bttn_input_error);
                    jumlahkelas.requestFocus();
                    bttnContinue.setText("SIGN IN");
                    bttnContinue.setEnabled(true);
                }
                else
                {
                    reference = FirebaseDatabase.getInstance().getReference().
                            child("Kontingen").child(kontingen.getText().toString()); // Menyimpan data sesuai username yang terletak di Tabel Users
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataSnapshot.getRef().child("username").setValue(username_key_new); // data username ke firebase
                            dataSnapshot.getRef().child("kontingen").setValue(kontingen.getText().toString()); // data username ke firebase
                            dataSnapshot.getRef().child("manager").setValue(manager.getText().toString()); // data password ke firebase
                            dataSnapshot.getRef().child("nohpmanager").setValue(nohpmanager.getText().toString()); // data email_address ke firebase
                            dataSnapshot.getRef().child("official").setValue(official.getText().toString()); // data username ke firebase
                            dataSnapshot.getRef().child("nohpofficial").setValue(nohpofficial.getText().toString()); // data password ke firebase
                            dataSnapshot.getRef().child("pelatih").setValue(pelatih.getText().toString()); // data email_address ke firebase
                            dataSnapshot.getRef().child("nohppelatih").setValue(nohppelatih.getText().toString()); // data email_address ke firebase
                            dataSnapshot.getRef().child("jumlahkelas").setValue(jumlahkelas.getText().toString()); // data email_address ke firebase


                            Toast.makeText(getApplicationContext(), "Data Berhasil Masuk", Toast.LENGTH_SHORT).show();
                            Intent gotonewaccountdua = new Intent(NewAccountDuaAct.this,NewAccountTigaAct.class);
                            gotonewaccountdua.putExtra("nama_kontingen",kontingen.getText().toString());
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
            startActivity(new Intent(NewAccountDuaAct.this, TentangAct.class));
        }
        return true;
    }
}
