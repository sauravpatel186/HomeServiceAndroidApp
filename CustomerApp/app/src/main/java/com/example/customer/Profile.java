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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity {
    EditText email, fname, address, mobile;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    FirebaseFirestore db;
    Task<DocumentSnapshot> documentReference;
    String uid;
    DatabaseReference reff;

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    Button logout, update, delete;
    ImageView home;
    TextView txt;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_profile);
        ConstraintLayout constraintLayout = findViewById(R.id.profile);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        checkconnection();
        email = (EditText) findViewById(R.id.edtemail);
        fname = (EditText) findViewById(R.id.edtfname);
        address = (EditText) findViewById(R.id.edtaddress);
        mobile = (EditText) findViewById(R.id.edtmobile);
        logout = (Button) findViewById(R.id.logoutbtn);
        update = (Button) findViewById(R.id.btnupdate);
        delete = (Button) findViewById(R.id.btndelete);
        home=(ImageView) findViewById(R.id.homeimg);
        txt=findViewById(R.id.txtemail);
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Profile.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        db=FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = mAuth.getUid();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Profile.this,Home.class));
                finish();

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                finish();
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this, Updateprofile.class);
                i.putExtra("fname", fname.getText().toString());
                i.putExtra("email", email.getText().toString());
                i.putExtra("address", address.getText().toString());
                i.putExtra("mobile", mobile.getText().toString());
                finish();
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(user.isEmailVerified())
        {
            Log.d("val",String.valueOf(user.isEmailVerified()));
            txt.setText("");
        }
        else
        {
            txt.setText("* Email Is Not Verified Click Here To Verify");
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Profile.this,"Verification Link Send To Registered Email-id",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Profile.this,"Error While Sending Verification Link",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }

        documentReference=db.collection("Customer").document(uid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists())
                {
                    String fullname = documentSnapshot.get("fname").toString();
                    String mail = documentSnapshot.get("mail").toString();
                    String mobileno = documentSnapshot.get("mobile").toString();
                    String add =documentSnapshot.get("address").toString();
                    email.setText(mail);
                    fname.setText(fullname);
                    address.setText(add);
                    mobile.setText(mobileno);
                }
                else
                {
                    //
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
          /* reff = FirebaseDatabase.getInstance().getReference().child("Customer").child(uid);
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }*/
            /*@Override
            public void onClick(View v) {


        }*/


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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Profile.this);
        builder.setTitle("Network Connection Error")
                .setMessage("You Have No Internet Connection")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Profile.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
}