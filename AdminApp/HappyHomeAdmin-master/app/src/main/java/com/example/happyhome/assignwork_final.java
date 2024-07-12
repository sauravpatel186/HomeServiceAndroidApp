package com.example.happyhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class assignwork_final extends AppCompatActivity {
    TextView fcustname,fpname,fcategory,fstech;
    Button aw;
    String pid,tid,cmob,tmob,techname,message,date;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignwork_final);
        //animation code
        ConstraintLayout constraintLayout = findViewById(R.id.p4);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        //end
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        date = df.format(Calendar.getInstance().getTime());

        fpname=findViewById(R.id.fpname);
        fcategory=findViewById(R.id.fcategory);
        fstech=findViewById(R.id.fstech);
        aw=findViewById(R.id.btnaw);
        cmob=getIntent().getStringExtra("cmob");
        tmob=getIntent().getStringExtra("tmobile");
        pid=getIntent().getStringExtra("pid");
        tid=getIntent().getStringExtra("tid");
        techname=getIntent().getStringExtra("fullname");
      
        fpname.setText(getIntent().getStringExtra("pname"));
        fcategory.setText(getIntent().getStringExtra("category"));
        fstech.setText(getIntent().getStringExtra("fullname"));
        progressDialog = new ProgressDialog(assignwork_final.this);
        aw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Assigning Work...");
                progressDialog.show();
                Map<String, Object> data = new HashMap<>();
                data.put("status", "Work Assigned");
                data.put("tech_id", tid);
                data.put("notify",1);
                db.collection("Problem_Post").document(pid).update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(assignwork_final.this, "Work Assigned", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(assignwork_final.this, home.class);
                        startActivity(i);
                        sendSMStocust();

                    }
                });
            }
        });

    }
    public void sendSMStocust() {

        message ="Your Work Is Assigned to " + techname +" At "+date;
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(cmob, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.",Toast.LENGTH_LONG).show();
    }
}