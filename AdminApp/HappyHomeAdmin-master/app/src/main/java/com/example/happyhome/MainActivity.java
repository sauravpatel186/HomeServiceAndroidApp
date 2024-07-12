package com.example.happyhome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity{
    Button login_btn;
    EditText email,upass;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //animation code
        LinearLayout constraintLayout = findViewById(R.id.p11);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        //end
        mFirebaseAuth = FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.email);
        upass=(EditText)findViewById(R.id.password);
        login_btn=(Button)findViewById(R.id.login_btn);
        progressDialog = new ProgressDialog(MainActivity.this);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    Toast.makeText(MainActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, home.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };
        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String mail=email.getText().toString();
                String pwd=upass.getText().toString();
                progressDialog.setTitle("Verifying Details...");
                progressDialog.show();
                if((mail.isEmpty()))
                {
                    progressDialog.dismiss();
                    email.setError("Username Cannot be Empty");
                    email.requestFocus();

                }
                else if(pwd.isEmpty())
                {
                    progressDialog.dismiss();
                    upass.setError("Password Cannot be Empty");
                    upass.requestFocus();

                }

                else  if(!(email.getText().toString().isEmpty() && upass.getText().toString().isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(mail, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {


                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                finish();
                                Intent intToHome = new Intent(MainActivity.this, home.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Error Occurred!",Toast.LENGTH_LONG).show();

                }

            }
        });
    };
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }
}