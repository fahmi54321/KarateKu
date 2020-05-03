package com.example.karateku;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UploadScanAct extends AppCompatActivity {

    Button bttnUploadKK,bttnUploadKtp,bttnUploadKitas,bttnUploadPassport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_scan);

        Bundle bundle = getIntent().getExtras();
        final String atletku=bundle.getString("atlet_baru_bana");


        bttnUploadKK = findViewById(R.id.bttnUploadKK);
        bttnUploadKtp = findViewById(R.id.bttnUploadKtp);
        bttnUploadKitas = findViewById(R.id.bttnUploadKitas);
        bttnUploadPassport = findViewById(R.id.bttnUploadPassport);

        bttnUploadKK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotophotokk = new Intent(UploadScanAct.this,PhotoKKAct.class);
                gotophotokk.putExtra("fix_atlet",atletku);
                startActivity(gotophotokk);
            }
        });

        bttnUploadKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotophotokk = new Intent(UploadScanAct.this,PhotoKtpAct.class);
                gotophotokk.putExtra("fix_atlet",atletku);
                startActivity(gotophotokk);
            }
        });

        bttnUploadKitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotophotokk = new Intent(UploadScanAct.this,PhotoKitasAct.class);
                gotophotokk.putExtra("fix_atlet",atletku);
                startActivity(gotophotokk);
            }
        });

        bttnUploadPassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotophotokk = new Intent(UploadScanAct.this,PhotoPassportAct.class);
                gotophotokk.putExtra("fix_atlet",atletku);
                startActivity(gotophotokk);
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
            startActivity(new Intent(UploadScanAct.this, TentangAct.class));
        }
        return true;
    }
}
