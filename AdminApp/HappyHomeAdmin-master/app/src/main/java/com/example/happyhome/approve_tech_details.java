package com.example.happyhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class approve_tech_details extends AppCompatActivity {
    TextView a, b, c;
    String uid, id;
    Button approve,deny,certi;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_tech_details);
        //animation code
        ConstraintLayout constraintLayout = findViewById(R.id.p2);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        //end
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        id = getIntent().getStringExtra("id");
        Calendar ct = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy & HH:mm:ss");
        final String date = dateFormat.format(new Date());
        a = findViewById(R.id.txtat_name);
        a.setText(getIntent().getStringExtra("fullname"));
        a.setFocusableInTouchMode(false);
        b = findViewById(R.id.txtat_category);
        b.setText(getIntent().getStringExtra("category"));
        b.setFocusableInTouchMode(false);
        c = findViewById(R.id.txtat_dob);
        c.setText(getIntent().getStringExtra("dob"));
        c.setFocusableInTouchMode(false);
        approve=findViewById(R.id.btnat_approve);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("status","approved");
                db.collection("Technician").document(id).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(approve_tech_details.this,"Technician Approved",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(approve_tech_details.this,home.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(approve_tech_details.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        deny=findViewById(R.id.btnat_deny);
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("status","deny");
                db.collection("Technician").document(id).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(approve_tech_details.this,"Technician Denied",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(approve_tech_details.this,home.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(approve_tech_details.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        certi=findViewById(R.id.btnat_certi);
        certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(approve_tech_details.this,Cerificate_view.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });
    }
}