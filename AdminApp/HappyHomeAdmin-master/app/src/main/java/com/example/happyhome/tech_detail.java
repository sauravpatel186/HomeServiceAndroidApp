package com.example.happyhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class tech_detail extends AppCompatActivity {
    TextView a, b, c, d, e,f;
    String uid, id;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    Button certi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_detail);
        //animation code
        ConstraintLayout constraintLayout = findViewById(R.id.p12);
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
        a = findViewById(R.id.techd_name);
        a.setText(getIntent().getStringExtra("fullname"));
        a.setFocusableInTouchMode(false);
        b = findViewById(R.id.techd_category);
        b.setText(getIntent().getStringExtra("category"));
        b.setFocusableInTouchMode(false);
        c = findViewById(R.id.techd_mail);
        c.setText(getIntent().getStringExtra("mail"));
        c.setFocusableInTouchMode(false);
        d = findViewById(R.id.techd_dob);
        d.setText(getIntent().getStringExtra("dob"));
        d.setFocusableInTouchMode(false);
        e = findViewById(R.id.techd_address);
        e.setText(getIntent().getStringExtra("address"));
        e.setFocusableInTouchMode(false);
        f = findViewById(R.id.techd_mobile);
        f.setText(getIntent().getStringExtra("mobile"));
        f.setFocusableInTouchMode(false);
        certi=findViewById(R.id.btn_certi);
        certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tech_detail.this,Cerificate_view.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });
    }
}