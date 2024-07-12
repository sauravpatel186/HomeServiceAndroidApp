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
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText uname,password;
    Button login;
    TextView signup,fpass;
    private FirebaseAuth mAuth;
    String user,pass,url;
    ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LinearLayout linearLayout = findViewById(R.id.login);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        checkconnection();
        uname=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        mAuth=FirebaseAuth.getInstance();
        fpass=findViewById(R.id.fpassword);
        progressDialog = new ProgressDialog(MainActivity.this);
        login=(Button)findViewById(R.id.login_btn);
       // url="https://on-stream-attribute.000webhostapp.com/home/login.php";
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, Home.class);
                    finish();
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };
        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                if(uname.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                    uname.setError("Email Cannot Be Empty");
                }
                else if(password.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                    password.setError("Password Cannot Be Empty");
                }
                else
                {
                     user=uname.getText().toString();
                     pass=password.getText().toString();
                    progressDialog.setTitle("Validating Details...");
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if(!task.isSuccessful())
                                     {
                                            progressDialog.dismiss();
                                         Toast.makeText(MainActivity.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();

                                     }
                                     else
                                     {


                                         startActivity(new Intent(MainActivity.this,Home.class));
                                         progressDialog.dismiss();
                                         finish();

                                     }
                                 }
                             });
//                    background bg=new background();
//                    bg.execute(user,pass);

                //    new InsertData().execute();

                }

            }
        });
        fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,forgotpassword.class);
                startActivity(i);
            }
        });
        signup=(TextView)findViewById(R.id.txtsignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Registration.class);
                startActivity(i);
            }
        });

}




    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthStateListener);

    }

/*class InsertData extends AsyncTask<Void,Void,Void>{


        JSONParser js;
        String result;
        ProgressDialog pd;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(MainActivity.this);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.setIndeterminate(false);
        pd.show();
    }

  /*  @Override
   protected Void doInBackground(Void... voids) {

        js=new JSONParser();
        HashMap<String, String> params = new HashMap<>();
        params.put("email", user.trim());
        params.put("password", pass.trim());
        result=js.sendPostRequest(url,params);
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(pd.isShowing()){
            pd.dismiss();
        }//
        Toast.makeText(MainActivity.this, ""+result, Toast.LENGTH_SHORT).show();

        Intent i=new Intent(MainActivity.this,Home.class);
        startActivity(i);

    }
}
*/
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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Network Connection Error")
                .setMessage("You Have No Internet Connection")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
}
