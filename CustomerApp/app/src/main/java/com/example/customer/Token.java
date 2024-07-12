package com.example.customer;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Token extends AppCompatActivity {
    Button btn;
    TextView pn,amount;
    FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reff;
    String name,date,address,mobile,cat,pdesc,pname,mail,uid,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        LinearLayout constraintLayout = findViewById(R.id.token);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        checkconnection();
        pn=findViewById(R.id.pname);
        mAuth=FirebaseAuth.getInstance();
        uid=mAuth.getCurrentUser().getUid();
        pn.setText(getIntent().getStringExtra("pname"));
        amount=findViewById(R.id.txtamt);
        btn=(Button)findViewById(R.id.paybtn);

        reff=FirebaseDatabase.getInstance().getReference();
        name=getIntent().getStringExtra("fname");
        date=getIntent().getStringExtra("date");
         mail=getIntent().getStringExtra("mail");
       address=getIntent().getStringExtra("address");
         mobile=getIntent().getStringExtra("mobile");
         pdesc=getIntent().getStringExtra("pdesc");
         pname=getIntent().getStringExtra("pname");
         cat=getIntent().getStringExtra("cate");
        status=getIntent().getStringExtra("status");

                btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Token.this,Payment.class);
                i.putExtra("amount",100);
                i.putExtra("mail",mail);
                i.putExtra("fname",name);
                i.putExtra("address",address);
                i.putExtra("mobile",mobile);
                i.putExtra("pname",pname);
                i.putExtra("pdesc",pdesc);
                i.putExtra("cate",cat);
                i.putExtra("date",date);
                i.putExtra("uid",uid);
                i.putExtra("amount",amount.getText().toString());
                i.putExtra("status",status);
                finish();
                startActivity(i);
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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Token.this);
        builder.setTitle("Network Connection Error")
                .setMessage("You Have No Internet Connection")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Token.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
}
