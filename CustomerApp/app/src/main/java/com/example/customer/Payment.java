package com.example.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Payment extends AppCompatActivity {
    EditText amt;
    Button btn;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    EditText visaname,cvc,number;
    Task<Void> documentReference;
    String name,date,address,mobile,cat,pdesc,pname,mail,uid,amount,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        checkconnection();
        amt=findViewById(R.id.VISAAMOUNT);

        btn=(Button)findViewById(R.id.SUBMITBTN);
        visaname=(EditText)findViewById(R.id.VISANAME);
        cvc=(EditText)findViewById(R.id.VISACVC);
        number=(EditText)findViewById(R.id.VISANUMBER);
        mAuth= FirebaseAuth.getInstance();
        uid=mAuth.getCurrentUser().getUid();
        name=getIntent().getStringExtra("fname");
        date=getIntent().getStringExtra("date");
        mail=getIntent().getStringExtra("mail");
        address=getIntent().getStringExtra("address");
        mobile=getIntent().getStringExtra("mobile");
        pdesc=getIntent().getStringExtra("pdesc");
        pname=getIntent().getStringExtra("pname");
        cat=getIntent().getStringExtra("cate");
        amount=getIntent().getStringExtra("amount");
        amt.setText(amount);
        status=getIntent().getStringExtra("status");
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(visaname.equals(""))
                {
                    visaname.setError("Name Cannot be Empty");
                }
                else if(cvc.equals("")||((cvc.length()<3)||(cvc.length()>3)))
                {
                  cvc.setError("Cannot be Empty or Cannot larger or Greater than 3 ");
                }
                else if((number.length()<14||number.length()>14)||number.equals(""))
                {
                    number.setError("Cannot be less than 14 or greater than 14");
                }
                else {
                    Intent i = new Intent(Payment.this, afterpayment.class);
                    i.putExtra("amount", "100");
                    i.putExtra("mail", mail);
                    i.putExtra("fname", name);
                    i.putExtra("address", address);
                    i.putExtra("mobile", mobile);
                    i.putExtra("pname", pname);
                    i.putExtra("pdesc", pdesc);
                    i.putExtra("cate", cat);
                    i.putExtra("date", date);
                    i.putExtra("uid", uid);
                    i.putExtra("status", status);
                    finish();
                    startActivity(i);
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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Payment.this);
        builder.setTitle("Network Connection Error")
                .setMessage("You Have No Internet Connection")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Payment.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
}
