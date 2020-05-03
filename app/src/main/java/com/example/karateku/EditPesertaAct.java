package com.example.karateku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EditPesertaAct extends AppCompatActivity {

    TextView nama_atlet,ttl;
    Button bttnSave;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    DatabaseReference reference,reference2,reference3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_peserta);
        Bundle bundle = getIntent().getExtras();

        final String kiriman_baru=bundle.getString("kiriman");
        getUsernameLocal();

        nama_atlet = findViewById(R.id.nama_atlet);
        ttl = findViewById(R.id.ttl);
        bttnSave = findViewById(R.id.bttnSave);

        reference = FirebaseDatabase.getInstance().getReference().child("Peserta").child(username_key_new).child(kiriman_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_atlet.setText(dataSnapshot.child("nama_atlet").getValue().toString());
                String parent  = dataSnapshot.getKey();
                ttl.setText(dataSnapshot.child("ttl").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        bttnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                reference3 = FirebaseDatabase.getInstance().getReference().child("Peserta").child(username_key_new);
//                reference3.orderByChild("nama_atlet").equalTo(nama_atlet.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        dataSnapshot.getRef().child("nama_atlet").setValue(nama_atlet.getText().toString());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

//

                reference2 = FirebaseDatabase.getInstance().getReference().child("Peserta").child(username_key_new).child(kiriman_baru);
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_atlet").setValue(nama_atlet.getText().toString()); // data username ke firebase
                        dataSnapshot.getRef().child("ttl").setValue(ttl.getText().toString()); // data password ke firebase
                        Toast.makeText(getApplicationContext(), "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();

                        Intent gotonewaccountdua = new Intent(EditPesertaAct.this,PesertaDetails.class);
                        startActivity(gotonewaccountdua);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();

                    }
                });

//                reference3 = FirebaseDatabase.getInstance().getReference().child("Peserta").child(username_key_new).child(nama_atlet.getText().toString());
//                reference3.orderByChild("nama_atlet").equalTo(nama_atlet.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        dataSnapshot.getRef().child("nama_atlet").setValue(nama_atlet.getText().toString()); // data username ke firebase
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

//                reference3 = FirebaseDatabase.getInstance().getReference().child("Peserta").child(username_key_new).child(nama_atlet.getText().toString());
//                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        dataSnapshot.getRef().child("nama_atlet").setValue(nama_atlet.getText().toString()); // data username ke firebase
//
//                        Toast.makeText(getApplicationContext(), "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
//
//                        Intent gotonewaccountdua = new Intent(EditPesertaAct.this,PesertaDetails.class);
//                        startActivity(gotonewaccountdua);
//                        finish();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
                reference3= FirebaseDatabase.getInstance().getReference().child("Peserta").child(username_key_new).child(kiriman_baru);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String key = dataSnapshot.getKey();
                        Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
                        dataSnapshot.getRef().child(key).setValue(nama_atlet.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
