package com.example.happyhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class assignwork_techdetails extends AppCompatActivity {
    RecyclerView awtech_group;

    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference awtechref=db.collection("Technician");
    private assignwork_techdetailAdapter adapter;
    String cfullname,pname,category1,pid,cmob;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignwork_techdetails);
        //animation code
        ConstraintLayout constraintLayout = findViewById(R.id.p5);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        //end
        mFirebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        cfullname=getIntent().getStringExtra("cfname");
        pname=getIntent().getStringExtra("pname");
        category1=getIntent().getStringExtra("category");
        pid=getIntent().getStringExtra("id");
        cmob=getIntent().getStringExtra("cmob");
        awtech_group = findViewById(R.id.awtechgroup);
        awtech_group.setLayoutManager(new LinearLayoutManager(this));
        Query query=awtechref.whereEqualTo("category",category1);
        FirestoreRecyclerOptions<techgroup> options =new FirestoreRecyclerOptions.Builder<techgroup>().setQuery(query,techgroup.class).build();
        adapter=new assignwork_techdetailAdapter(options);
        awtech_group.setAdapter(adapter);
        adapter.setOnItemClickListener(new assignwork_techdetailAdapter.OnItemClickListener(){
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                techgroup tech=documentSnapshot.toObject(techgroup.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                String fullname=documentSnapshot.getString("fullname");
                String category=documentSnapshot.getString("category");
                String mobile=documentSnapshot.getString("mobile");
                Intent i = new Intent(assignwork_techdetails.this,assignwork_final.class);
                i.putExtra("tid",id);
                i.putExtra("fullname",fullname);
                i.putExtra("category",category);
                i.putExtra("tmobile",mobile);
                i.putExtra("customername",cfullname);
                i.putExtra("pname",pname);
                i.putExtra("pid",pid);
                i.putExtra("cmob",cmob);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
