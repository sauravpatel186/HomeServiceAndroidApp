package com.example.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ongoingappointment extends AppCompatActivity {
    String name, desc, s, category;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String uid;
    FirebaseUser user;
    Button cancel;
    TextView pname, pdesc, cat, status;
    String id;
    String pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoingappointment);
        cancel = findViewById(R.id.cancelbtn);
        pname = findViewById(R.id.opname);
        pdesc = findViewById(R.id.opdesc);
        cat = findViewById(R.id.ocat);
        status = findViewById(R.id.ostatus);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        final ProgressDialog progressDialog=new ProgressDialog(ongoingappointment.this);
        db = FirebaseFirestore.getInstance();
        String[] st={"Work Accepted","n/a"};
       List<String> list=new ArrayList<String>();
       list.add("n/a");
       list.add("Work Accepted");
       Task<QuerySnapshot> query=db.collection("Problem_Post").whereIn("status",list).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                   Problem problem = documentSnapshot.toObject(Problem.class);
                   problem.setId(documentSnapshot.getId());
                   id = problem.getId();
                   Log.d("id",id);
                   s = problem.getStatus();
                   name = problem.getPname();
                   desc = problem.getPdesc();
                   category = problem.getCategory();
                   status.setText(s);
                   pdesc.setText(desc);
                   cat.setText(category);
                   pname.setText(name);
               }
           }
       });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ongoingappointment.this);
                builder.setTitle("Alert");
                builder.setMessage("Are you sure you want to cancel appointment");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        if (s.equals("Work Accepted")) {
                            Task<QuerySnapshot> query1 = db.collection("Problem_Accepted").whereEqualTo("pid", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        Problem problem = documentSnapshot.toObject(Problem.class);
                                        problem.setId(documentSnapshot.getId());
                                        pid = problem.getId();
                                        Log.d("pid",pid);
                                    }
                                       Task<Void> documentReference= db.collection("Problem_Accepted").document(pid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Task<Void> documentReference1=db.collection("Problem_Post").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Intent i = new Intent(ongoingappointment.this, AfterCancelAppointment.class);
                                                        i.putExtra("id", pid);
                                                        startActivity(i);
                                                        progressDialog.dismiss();

                                                    }
                                                });


                                            }
                                        });
                                    }



                            });

                        }
                        if(s.equals("n/a"))
                        {
                            Task<Void> documentReference1=db.collection("Problem_Post").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent i = new Intent(ongoingappointment.this, AfterCancelAppointment.class);
                                    startActivity(i);
                                    progressDialog.dismiss();
                                    finish();
                                }
                            });
                        }
                    }
                    });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }
}

