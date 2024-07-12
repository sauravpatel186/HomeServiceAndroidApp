package com.example.happyhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class Cerificate_view extends AppCompatActivity {
String id,uid;
ImageView certi;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    DocumentReference documentReference;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    private StorageReference mStorage;
    FirebaseUser user;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerificate_view);
        //animation code
        ConstraintLayout constraintLayout = findViewById(R.id.p6);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        //end
        progressDialog = new ProgressDialog(Cerificate_view.this);
        id = getIntent().getStringExtra("id");
        certi=findViewById(R.id.certi);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        downloadimage();


    }
    public void downloadimage()
    {
        try{
            progressDialog.setTitle("Fetching Image From Firebase....");
            progressDialog.show();
            StorageReference mImageRef = FirebaseStorage.getInstance().getReference("Certificates/"+id);
            final long ONE_MEGABYTE = 1024 * 1024;
            mImageRef.getBytes(ONE_MEGABYTE)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            DisplayMetrics dm = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(dm);
                            certi.setMinimumHeight(dm.heightPixels);
                            certi.setMinimumWidth(dm.widthPixels);
                            certi.setImageBitmap(bm);
                            progressDialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Cerificate_view.this,"Error to Get Image",Toast.LENGTH_SHORT).show();
                }
            });

            /*storage.getReferenceFromUrl("gs://happy-homes-24135.appspot.com/").child("minion.jpg");
            final File file=File.createTempFile("image","jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Glide.with(Cerificate_view.this).load(file.getAbsolutePath()).into(certi);
                    /*Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                    certi.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Cerificate_view.this,"Error to Get Image",Toast.LENGTH_SHORT).show();
                }
            });

            /*db.collection("Certificate").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String url=documentSnapshot.getString("Certificate_url");
                    Log.d("img",url);
                    Glide.with(Cerificate_view.this).load(url).into(certi);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Cerificate_view.this,"Error to Get Image",Toast.LENGTH_SHORT).show();
                }
            });*/
        }
        catch (Exception e){
            Toast.makeText(Cerificate_view.this,"Error "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}