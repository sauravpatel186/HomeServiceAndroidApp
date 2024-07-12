package com.example.customer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

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
import com.google.firebase.database.core.Constants;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Problem_Post extends AppCompatActivity implements View.OnClickListener {
    ListView list_view;
    String userid, pdesc, pname, cate, fname, address, mobile, mail, email, date,status;
    Boolean flag=true;
    FirebaseUser user;
    String uid;
    Task<DocumentSnapshot> documentReference;
    Task<QuerySnapshot> documentReference1;
    FirebaseAuth mAuth;
    EditText cat, description, pn;
    TextView t;
    Button btn,bbtn;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_problempost);
        LinearLayout linearLayout = findViewById(R.id.problempost);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        checkconnection();
        bbtn=findViewById(R.id.btnappointment);
        cat = (EditText) findViewById(R.id.edtcat);
        description = (EditText) findViewById(R.id.edtdesc);
        pn = (EditText) findViewById(R.id.edtpname);
        btn = (Button) findViewById(R.id.postbtn);
        t=(TextView)findViewById(R.id.txt);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
        date = df.format(c.getTime());

        //Firebase Code
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        uid = user.getUid();
        cat.setText(getIntent().getStringExtra("tech_category"));
        btn.setOnClickListener(this);

        userid = mAuth.getUid();//Current userid from database
        Log.d("userid", userid);
        //select * from Customer where uid="uid"
        final DocumentReference documentReference = db.collection("Customer").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fname = documentSnapshot.getString("fname");
                mobile = documentSnapshot.getString("mobile");
                mail = documentSnapshot.getString("mail");
                mobile = documentSnapshot.getString("mobile");
                address = documentSnapshot.getString("address");
            }
        });
       /*documentReference1=db.collection("Problem_Post").whereEqualTo("mail",mail).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
    {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {

                }
            }
            } );

    }*/
       try {
           Task<QuerySnapshot> query = db.collection("Problem_Post").whereEqualTo("uid", uid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
               @Override
               public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                   for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                       Problem problem = documentSnapshot.toObject(Problem.class);
                       problem.setId(documentSnapshot.getId());
                       String id = problem.getId();
                       Log.d("id", id);
                       status = problem.getStatus();
                       Log.d("status", status);
                       try {
                           if (status.equals("n/a") || status.equals("Work Accepted")) {

                               btn.setEnabled(false);
                               flag=false;
                               t.setText("* You Cannot book another appointment while another appointment is ongoing.");


                           }
                       }
                       catch(NullPointerException e)
                       {

                       }
                   }


               }
           });
       }
       catch (NullPointerException e)
       {

           btn.setEnabled(true);

       }
        bbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==true)
                {
                    Toast.makeText(Problem_Post.this,"No Ongoing Appointment",Toast.LENGTH_SHORT).show();

                }
                else if(flag==false)
                {
                    startActivity(new Intent(Problem_Post.this,ongoingappointment.class));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

            pname = pn.getText().toString().trim();
            pdesc = description.getText().toString().trim();
            cate = cat.getText().toString().trim();
            final AlertDialog.Builder dialog = new AlertDialog.Builder(Problem_Post.this);
            dialog.setTitle("Alert");
            dialog.setMessage("If you want to book an appointment you need to book an appointment otherwise you won't be able to book an appointment");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(Problem_Post.this, Token.class);
                    i.putExtra("mail", mail);
                    i.putExtra("fname", fname);
                    i.putExtra("address", address);
                    i.putExtra("mobile", mobile);
                    i.putExtra("pname", pname);
                    i.putExtra("pdesc", pdesc);
                    i.putExtra("cate", cate);
                    i.putExtra("date", date);
                    i.putExtra("status", "n/a");
                    startActivity(i);

                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();

    }


    protected  void onStart() {
        super.onStart();

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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Problem_Post.this);
        builder.setTitle("Network Connection Error")
                .setMessage("You Have No Internet Connection")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Problem_Post.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                });
        android.app.AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
}


