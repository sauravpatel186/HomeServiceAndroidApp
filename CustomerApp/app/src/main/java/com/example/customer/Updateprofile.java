package com.example.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Updateprofile extends AppCompatActivity implements View.OnClickListener {
EditText nemail,nfname,nmobile,naddress;
Button btn,delete;
FirebaseAuth mAuth;
FirebaseDatabase firebaseDatabase;
FirebaseFirestore db;
FirebaseUser user;
Task<Void> documentReference;
String uid;
ProgressDialog progressDialog;
DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);
        ConstraintLayout constraintLayout = findViewById(R.id.updateprofile);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        checkconnection();
        nemail=(EditText)findViewById(R.id.editemail);
        nfname=(EditText)findViewById(R.id.editfname);
        nmobile=(EditText)findViewById(R.id.editmobile);
        naddress=(EditText)findViewById(R.id.editaddress);
        btn=(Button)findViewById(R.id.updatebtn);
        delete=findViewById(R.id.btndelete);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        progressDialog=new ProgressDialog(Updateprofile.this);
        user=FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(Updateprofile.this);
               dialog.setTitle("Alert");
               dialog.setMessage("Are You Sure you want to delete your account.");
               dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       progressDialog.setTitle("Deleting Your Account");
                       progressDialog.show();
                       documentReference=db.collection("Customer").document(uid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       progressDialog.dismiss();
                                       if (task.isSuccessful())
                                       {

                                           startActivity(new Intent(Updateprofile.this,MainActivity.class));
                                       }


                                   }
                               });

                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(Updateprofile.this,"Error while deleting ypu account",Toast.LENGTH_LONG).show();
                           }
                       });
                   }
               });
               dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                   }
               });
                AlertDialog alertDialog=dialog.create();
                alertDialog.show();
            }
        });
       btn.setOnClickListener(this);
        nemail.setText(getIntent().getStringExtra("email"));
        nfname.setText(getIntent().getStringExtra("fname"));
        nmobile.setText(getIntent().getStringExtra("mobile"));
        naddress.setText(getIntent().getStringExtra("address"));


   }




    @Override
    public void onClick(View v) {
        String mail=nemail.getText().toString();
        String fname=nfname.getText().toString();
        String address=naddress.getText().toString();
        String mobile=nmobile.getText().toString();
        Map<String,Object> up=new HashMap<>();
        up.put("address",address);
        up.put("fname",fname);
        up.put("mobile",mobile);
        up.put("mail",mail);
        progressDialog.setTitle("Updating");
        progressDialog.show();
        documentReference = db.collection("Customer").document(uid).update(up).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent i=new Intent(Updateprofile.this,Profile.class);
                Toast.makeText(Updateprofile.this,"Updated Successfully",Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                startActivity(i);


            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Updateprofile.this,"Failed To Update",Toast.LENGTH_SHORT).show();
            }
        });
       /* reff=firebaseDatabase.getInstance().getReference().child("Customer").child(uid);

            UserProfile user=new UserProfile(mail,fname,address,mobile) ;
            reff.setValue(user);
        Intent i=new Intent(Updateprofile.this,Profile.class);
        startActivity(i);*/

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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Updateprofile.this);
        builder.setTitle("Network Connection Error")
                .setMessage("You Have No Internet Connection")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Updateprofile.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }


}
