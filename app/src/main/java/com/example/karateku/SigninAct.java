package com.example.karateku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.karateku.R.drawable.bttn_input_error;

public class SigninAct extends AppCompatActivity  {

    EditText xusername,xpassword;
    TextView txtNewAccount;
    Button bttnSignin;
    ImageView logo;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    DatabaseReference referense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);

        txtNewAccount = findViewById(R.id.txtNewAccount);
        bttnSignin = findViewById(R.id.bttnSignin);

        txtNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonewaccount = new Intent(SigninAct.this,NewAccountSatuAct.class);
                startActivity(gotonewaccount);
            }
        });

        bttnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bttnSignin.setText("LOADING...");
                bttnSignin.setEnabled(false);

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();
                if(username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username Kosong", Toast.LENGTH_SHORT).show();
                    xusername.setBackgroundResource(bttn_input_error);
                    xusername.requestFocus();
                    bttnSignin.setText("SIGN IN");
                    bttnSignin.setEnabled(true);
                }
                else
                {
                    if (password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Password Kosong", Toast.LENGTH_SHORT).show();
                        xpassword.setBackgroundResource(bttn_input_error);
                        xpassword.requestFocus();
                        bttnSignin.setText("SIGN IN");
                        bttnSignin.setEnabled(true);
                    }
                    else
                    {
                        //Tahap 2
                        referense = FirebaseDatabase.getInstance().getReference().child("Users").child(xusername.getText().toString());

                        referense.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists())
                                {
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    if(password.equals(passwordFromFirebase))
                                    {
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key,xusername.getText().toString());
                                        editor.apply();

                                        Toast.makeText(getApplicationContext(), "Berhasil Login", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(SigninAct.this,LoginSuccessAct.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Password Tidak Ada",Toast.LENGTH_SHORT).show();
                                        xpassword.setBackgroundResource(bttn_input_error);
                                        xpassword.requestFocus();
                                        bttnSignin.setText("SIGN IN");
                                        bttnSignin.setEnabled(true);
                                    }
                                }
                                else
                                {
                                    bttnSignin.setText("SIGN IN");
                                    bttnSignin.setEnabled(true);
                                    Toast.makeText(getApplicationContext(),"Username Tidak Ada",Toast.LENGTH_SHORT).show();
                                    xusername.setBackgroundResource(bttn_input_error);
                                    xusername.requestFocus();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });
    }
}
