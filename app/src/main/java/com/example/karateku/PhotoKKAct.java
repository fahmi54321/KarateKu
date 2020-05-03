package com.example.karateku;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PhotoKKAct extends AppCompatActivity {

    Button bttnUploadScanKK,bttnContinue;
    ImageView ScanKK;

    //Tahap 1 Menentukan variabel untuk menentukan lokasi foto serta variabel untuk maksimal photo untuk input
    Uri photo_location;
    Integer photo_max=1;

    //Tahap 6
    DatabaseReference reference;
    StorageReference storage;

    //Tahap 7
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_kk);
        getUsernameLocal();

        Bundle bundle = getIntent().getExtras();
        final String nama=bundle.getString("fix_atlet");


        bttnUploadScanKK = findViewById(R.id.bttnUploadScanKK);
        bttnContinue = findViewById(R.id.bttnContinue);
        ScanKK = findViewById(R.id.ScanKK);

        bttnUploadScanKK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findphoto();
            }
        });

        bttnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tahap 10
                reference = FirebaseDatabase.getInstance().getReference().child("Peserta").child(nama);
                storage = FirebaseStorage.getInstance().getReference().child("PhotoScan").child("ScanKK").child(nama);

                bttnContinue.setText("LOADING...");
                bttnContinue.setEnabled(false);

                //Tahap 11 Cek File Apakah Ada ?
                if(photo_location !=null)
                {
                    StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));
                    storageReference1.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if(taskSnapshot.getMetadata()!=null)
                            {
                                final Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String imageUrl = uri.toString();
                                        reference.getRef().child("url_scan_kk").setValue(imageUrl);
                                    }
                                });
                            }
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Toast.makeText(getApplicationContext(), "Upload Berhasil", Toast.LENGTH_SHORT).show();
                            Intent gotosuccesregister = new Intent(PhotoKKAct.this,RegisterSuccessDuaAct.class);
                            startActivity(gotosuccesregister);
                            finish();
                        }
                    });
                }
            }
        });

    }

    // Tahap 5
    String getFileExtension(Uri uri)
    {
        // ContentProvider. Objek penyedia menerima permintaan data dari klien, melakukan aksi yang diminta, dan mengembalikan hasilnya
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    //Tahap 3 *Function untuk menemukan photo*
    public void findphoto()
    {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic,photo_max);
    }

    //Tahap 4
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==photo_max && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(ScanKK);
            bttnUploadScanKK.setVisibility(View.GONE);
        }
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
            startActivity(new Intent(PhotoKKAct.this, TentangAct.class));
        }
        return true;
    }
}
