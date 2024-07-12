package com.example.happyhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity{
    TextView approvetech,techdetail,custdetail,feedback,faq,exit;
    ImageView iapprovetech,itechdetail,icustdetail,ifeedback,ifaq,iexit;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //animation code
        ConstraintLayout constraintLayout = findViewById(R.id.p10);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        //end
        mFirebaseAuth = FirebaseAuth.getInstance();
        approvetech=(TextView)findViewById(R.id.txtapprove_tech);
        techdetail=(TextView)findViewById(R.id.txttechdetail);
        custdetail=(TextView)findViewById(R.id.txtcustdetail);
        feedback=(TextView)findViewById(R.id.txtfeed);
        faq=(TextView)findViewById(R.id.txtfaq);
        exit=(TextView)findViewById(R.id.txtexit);
        iapprovetech=(ImageView)findViewById(R.id.imgapp_tech);
        itechdetail=(ImageView)findViewById(R.id.imgtech_detail);
        icustdetail=(ImageView)findViewById(R.id.imgcustdetail);
        ifeedback=(ImageView)findViewById(R.id.imgfeed);
        ifaq=(ImageView)findViewById(R.id.imgfaq);
        iexit=(ImageView) findViewById(R.id.imgexit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutfun();
            }
        });
        iexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutfun();
            }
        });

        approvetech.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(home.this,approve_tech.class);
                startActivity(i);
            }
        });
        iapprovetech.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(home.this,approve_tech.class);
                startActivity(i);
            }
        });
        techdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(home.this,tech_group.class);
                startActivity(i);
            }
        });
        itechdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(home.this,tech_group.class);
                startActivity(i);
            }
        });
        custdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(home.this,cust_group.class);
                startActivity(i);
            }
        });
        icustdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(home.this,cust_group.class);
                startActivity(i);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(home.this,feedback.class);
                startActivity(i);
            }
        });
        ifeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(home.this,feedback.class);
                startActivity(i);
            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(home.this,assign_work.class);
                startActivity(i);
            }
        });
        ifaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(home.this,assign_work.class);
                startActivity(i);
            }
        });
    }
    public void logoutfun()
    {
        mFirebaseAuth.signOut();
        finish();
        startActivity(new Intent(home.this,MainActivity.class));
    }
}
