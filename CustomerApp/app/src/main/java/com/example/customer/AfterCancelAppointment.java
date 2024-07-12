package com.example.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AfterCancelAppointment extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String uid;
    private static int SPLASH_TIME_OUT=3000;
    FirebaseUser user;
 ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_cancel_appointment);
        mAuth=FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
    progressBar=findViewById(R.id.progressBar3);
        boolean b=new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(AfterCancelAppointment.this,Home.class);
                progressBar.setVisibility(View.VISIBLE);
                startActivity(intent);

            }
        },SPLASH_TIME_OUT);

    }
}
