package com.example.customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class feedback extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    FirebaseFirestore db;
    Task<DocumentReference> documentReference;
    Task<DocumentSnapshot> rf,reff;
    Button btn;
    EditText pname, pdesc, techname, comment;
    RatingBar ratingBar;
    String pid, tid, uid, c;
    String fullname;
    float r;
    String rating;
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ConstraintLayout constraintLayout = findViewById(R.id.feedback);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        checkconnection();
        pname = (EditText) findViewById(R.id.edtpname);
        pdesc = (EditText) findViewById(R.id.edtpdesc);
        techname = (EditText) findViewById(R.id.edttechname);
        comment = (EditText) findViewById(R.id.edtcomment);
        btn = findViewById(R.id.btnsubmit);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        techname.setText(getIntent().getStringExtra("name"));
        pname.setText(getIntent().getStringExtra("pname"));
        pdesc.setText(getIntent().getStringExtra("pdesc"));
        pid = getIntent().getStringExtra("id");
        tid = getIntent().getStringExtra("techid");
        mAuth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = mAuth.getCurrentUser().getUid();

        rf=db.collection("Customer").document(uid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                          @Override
                                          public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                  fullname= documentSnapshot.get("fname").toString();
                                                   Log.d("fname",fullname);
                                          }
                                      });
        try {
            Task<QuerySnapshot> query=db.collection("Feedback").whereEqualTo("pid",pid).whereEqualTo("uid",uid).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                            {
                                feedbackdata feedbackdata = documentSnapshot.toObject(feedbackdata.class);
                                feedbackdata.setId(documentSnapshot.getId());
                                rating=feedbackdata.getRating().toString();
                                Log.d("rating",rating);
                            }
                            try {
                                if (!rating.equals("")) {

                                    btn.setEnabled(false);
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(feedback.this);
                                    dialog.setTitle("Alert");
                                    dialog.setMessage("You cannot give feedback to this problem because you have already given feedback");
                                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(feedback.this,historyActivity.class));
                                            finish();
                                        }
                                    });
                                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    AlertDialog alertDialog = dialog.create();
                                    alertDialog.show();


                                }
                            }
                            catch(NullPointerException e)
                            {

                            }

                        }
                    });
        }
        catch (NullPointerException e)
        {
            btn.setEnabled(true);
        }
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                final int numStars = ratingBar.getNumStars();
                r = ratingBar.getRating();
                final float ratingBarStepSize = ratingBar.getStepSize();

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = comment.getText().toString();
                Map<String, Object> f = new HashMap<>();
                f.put("pid",pid);
                f.put("tech_id",tid);
                f.put("pname",pname.getText().toString());
                f.put("pdesc",pdesc.getText().toString());
                f.put("tech_name",techname.getText().toString());
                f.put("uid", uid);
                f.put("c_name",fullname);
                f.put("rating",String.valueOf(r));
                f.put("comment",c);
                Log.d("comment",c);
                documentReference=db.collection("Feedback").add(f).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        startActivity(new Intent(feedback.this,Home.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(feedback.this,"Error while giving feedback",Toast.LENGTH_SHORT).show();
                    }
                });

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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(feedback.this);
        builder.setTitle("Network Connection Error")
                .setMessage("You Have No Internet Connection")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(feedback.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                });
        android.app.AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
}


