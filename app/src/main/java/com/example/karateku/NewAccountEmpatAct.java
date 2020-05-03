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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewAccountEmpatAct extends AppCompatActivity {

    Button bttnUploadPhotoAtlet,bttnContinue,bttnView;
    ImageView PhotoAtlet;

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
        setContentView(R.layout.activity_new_account_empat);

        getUsernameLocal();

        Bundle bundle = getIntent().getExtras();
        final String atlet_baru=bundle.getString("atlet");


        bttnUploadPhotoAtlet = findViewById(R.id.bttnUploadPhotoAtlet);
        bttnContinue = findViewById(R.id.bttnContinue);
        PhotoAtlet = findViewById(R.id.PhotoAtlet);

        bttnUploadPhotoAtlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findphoto();
            }
        });


        bttnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tahap 10
                reference = FirebaseDatabase.getInstance().getReference().child("Peserta").child(atlet_baru);
                storage = FirebaseStorage.getInstance().getReference().child("PhotoAtlet").child(atlet_baru);

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
                                result.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"Gagal Upload Foto",Toast.LENGTH_SHORT);

                                    }
                                });
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();
                                        reference.getRef().child("url_photo_atlet").setValue(imageUrl);
                                    }
                                });
                            }
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            System.out.println("Upload is " + progress + "% done");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(),"Gagal Upload",Toast.LENGTH_SHORT);

                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Toast.makeText(getApplicationContext(), "Upload Berhasil", Toast.LENGTH_SHORT).show();
                            Intent gotosuccesregister = new Intent(NewAccountEmpatAct.this,UploadScanAct.class);
                            gotosuccesregister.putExtra("atlet_baru_bana",atlet_baru);
                            startActivity(gotosuccesregister);
                            finish();
                        }
                    });
                }
            }
        });

        bttnUploadPhotoAtlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findphoto();
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
            Picasso.with(this).load(photo_location).centerCrop().fit().into(PhotoAtlet);
            bttnUploadPhotoAtlet.setVisibility(View.GONE);
        }
    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

    public class MyFailureListener implements OnFailureListener {
        @Override
        public void onFailure(@NonNull Exception exception) {
            int errorCode = ((StorageException) exception).getErrorCode();
            String errorMessage = exception.getMessage();
            // test the errorCode and errorMessage, and handle accordingly
        }
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
            startActivity(new Intent(NewAccountEmpatAct.this, TentangAct.class));
        }
        return true;
    }
}
