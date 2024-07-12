package com.example.customer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Home extends AppCompatActivity {
    ImageView profileimg,homeimg,feedbackimg,carpenterimg,plumberimg,painterimg,mechanicimg,pestcontrolimg,electricianimg,contactusimg,payment_history;
    Button delete;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    String uid,status;
    DatabaseReference rf,reff;
    ProgressDialog progressDialog;
    private static  final String CHANNEL_1_ID="channel1";
    private static  final String CHANNEL_2_ID="channel2";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);
        ConstraintLayout constraintLayout = findViewById(R.id.home);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        checkconnection();
        profileimg=(ImageView) findViewById(R.id.profileimg);
        homeimg=(ImageView) findViewById(R.id.homeimg);
        feedbackimg=(ImageView) findViewById(R.id.feedbackimg);
        carpenterimg=(ImageView) findViewById(R.id.carpenterimg);
        mechanicimg=(ImageView) findViewById(R.id.mechanicimg);
        pestcontrolimg=(ImageView) findViewById(R.id.pestcontrolimg);
        electricianimg=(ImageView) findViewById(R.id.electricianimg);
        plumberimg=(ImageView) findViewById(R.id.plumberimg);
        painterimg=(ImageView) findViewById(R.id.painterimg);
        contactusimg=(ImageView) findViewById(R.id.contactus);
        payment_history=(ImageView) findViewById(R.id.paymentimg);
        delete=(Button)findViewById(R.id.btndelete);
        progressDialog=new ProgressDialog(Home.this);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();




       /* delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rf = FirebaseDatabase.getInstance().getReference().child("Customer").child(uid);


                AlertDialog.Builder dialog=new AlertDialog.Builder(Home.this);
                dialog.setTitle("Are you Sure?");
                dialog.setMessage("Deleting this account will result in completely removing your details you won't be able to access this app again.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        rf.removeValue();
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();

                                if(task.isSuccessful())
                                {

                                    Toast.makeText(Home.this,"Account Deleted",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(Home.this,MainActivity.class);
                                    startActivity(i);
                                }else
                                {
                                    Toast.makeText(Home.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog=dialog.create();
                alertDialog.show();
            }
        });*/
        profileimg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Profile.class);

                startActivity(i);
            }
        });
        contactusimg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,ContactUs.class);

                startActivity(i);
            }
        });
        payment_history.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this, Payment_History.class);

                startActivity(i);
            }
        });
        feedbackimg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this, historyActivity.class);
                startActivity(i);
            }
        });
        carpenterimg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Problem_Post.class);
                i.putExtra("tech_category","Carpenter");
                startActivity(i);
            }
        });
        electricianimg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Problem_Post.class);
                i.putExtra("tech_category","Electrican");

                startActivity(i);
            }
        });

        mechanicimg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Problem_Post.class);
                i.putExtra("tech_category","Mechanic");
                startActivity(i);
            }
        });
        pestcontrolimg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Problem_Post.class);
                i.putExtra("tech_category","Pest Control");
                startActivity(i);

            }
        });
        painterimg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Problem_Post.class);
               i.putExtra("tech_category","Painter");
                startActivity(i);
            }
        });
        plumberimg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Problem_Post.class);
                i.putExtra("tech_category","Plumber");
                startActivity(i);
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        Task<QuerySnapshot> query = db.collection("Problem_Post").whereEqualTo("uid", uid).whereEqualTo("status", "Work Accepted").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Problem problem = documentSnapshot.toObject(Problem.class);
                    problem.setId(documentSnapshot.getId());
                    String id = problem.getId();
                    status = problem.getStatus();
                    Log.d("stsus",status);
                    if(status.equals("Work Accepted"))
                    {


                    }
                }
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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Home.this);
        builder.setTitle("Network Connection Error")
                .setMessage("You Have No Internet Connection")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Home.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
}

