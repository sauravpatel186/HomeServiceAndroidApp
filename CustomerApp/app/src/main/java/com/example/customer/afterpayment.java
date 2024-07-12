package com.example.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class afterpayment extends AppCompatActivity {
    String name,date,address,mobile,cat,pdesc,pname,mail,uid,amount,status;
    Task<Void> documentReference,ref;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    ProgressBar progress;
    String d,id;
    Map<String,Object> t=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterpayment);
        ConstraintLayout constraintLayout = findViewById(R.id.afterpayment);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        checkconnection();
        mAuth=FirebaseAuth.getInstance();
        uid=mAuth.getCurrentUser().getUid();
        progress=findViewById(R.id.progressBar2);
        name=getIntent().getStringExtra("fname");
        date=getIntent().getStringExtra("date");
        mail=getIntent().getStringExtra("mail");
        address=getIntent().getStringExtra("address");
        mobile=getIntent().getStringExtra("mobile");
        pdesc=getIntent().getStringExtra("pdesc");
        pname=getIntent().getStringExtra("pname");
        cat=getIntent().getStringExtra("cate");
        amount=getIntent().getStringExtra("amount");
        status=getIntent().getStringExtra("status");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
        d = df.format(c.getTime());


        Map<String,Object> m= new HashMap<>();
        m.put("fname",name);
        m.put("mail",mail);
        m.put("address",address);
        m.put("mobile",mobile);
        m.put("uid",uid);
        m.put("pname",pname);
        m.put("pdesc",pdesc);
        m.put("date",date);
        m.put("category",cat);
        m.put("amount",amount);
        m.put("status",status);


        progress.setVisibility(View.VISIBLE);
        documentReference=db.collection("Problem_Post").document().set(m).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Task<QuerySnapshot> query=db.collection("Problem_Post").whereEqualTo("uid",uid).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    Problem problem = documentSnapshot.toObject(Problem.class);
                                    problem.setId(documentSnapshot.getId());
                                    id = problem.getId();
                                    Log.d("id",id);
                                    t.put("uid",uid);
                                    t.put("amount",amount);
                                    t.put("fname",name);
                                    t.put("mail",mail);
                                    t.put("category",cat);
                                    t.put("pname",pname);
                                    t.put("pdesc",pdesc);
                                    t.put("date",d);
                                    t.put("pid",id);
                                    ref=db.collection("Transaction").document().set(t).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent i=new Intent(afterpayment.this,Home.class);
                                            finish();
                                            startActivity(i);
                                        }
                                    });
                                }
                            }
                        });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public void checkconnection()
    {
        ConnectivityManager manager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=manager.getActiveNetworkInfo();

        if(null!=activeNetwork)
        {
            if(activeNetwork.getType()==manager.TYPE_WIFI){
                //Toast.makeText(this,"Wifi Enabled",Toast.LENGTH_LONG).show();
            }
            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // Toast.makeText(this,"Mobile Data Enabled",Toast.LENGTH_LONG).show();
            }

        }
        else {
            dialog();
            //Toast.makeText(this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }
    }
    public void dialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(afterpayment.this);
        builder.setTitle("Network Connection Error")
                .setMessage("You Have No Internet Connection")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(afterpayment.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
}
