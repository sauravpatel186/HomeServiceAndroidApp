package com.example.happyhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class cust_detail extends AppCompatActivity {
    EditText a, b, c, d, e;
    String uid, id;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_detail);
        //animation code
        ConstraintLayout constraintLayout = findViewById(R.id.p7);
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
        a = findViewById(R.id.cust_name);
        a.setText(getIntent().getStringExtra("fname"));
        a.setFocusableInTouchMode(false);
        b = findViewById(R.id.cust_mail);
        b.setText(getIntent().getStringExtra("mail"));
        b.setFocusableInTouchMode(false);

        d = findViewById(R.id.cust_mobile);
        d.setText(getIntent().getStringExtra("mobile"));
        d.setFocusableInTouchMode(false);
        e = findViewById(R.id.cust_address);
        e.setText(getIntent().getStringExtra("address"));
        e.setFocusableInTouchMode(false);
        a.setFocusableInTouchMode(false);
        b.setFocusableInTouchMode(false);

        d.setFocusableInTouchMode(false);
        e.setFocusableInTouchMode(false);

    }
}
