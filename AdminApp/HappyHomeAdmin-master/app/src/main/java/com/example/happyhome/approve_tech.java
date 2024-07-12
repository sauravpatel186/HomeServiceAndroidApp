package com.example.happyhome;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class approve_tech extends AppCompatActivity {
    RecyclerView apptech_group;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    public FirebaseFirestore db= FirebaseFirestore.getInstance();
    public CollectionReference apptechref=db.collection("Technician");
    public approvetechAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_tech);
        //animation code
        ConstraintLayout constraintLayout = findViewById(R.id.p1);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        //end
        mFirebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        apptech_group = findViewById(R.id.apptech_group);
        apptech_group.setLayoutManager(new LinearLayoutManager(this));
        Query query=apptechref.whereEqualTo("status","pending");
        FirestoreRecyclerOptions<approvetech> options =new FirestoreRecyclerOptions.Builder<approvetech>().setQuery(query,approvetech.class).build();
        adapter=new approvetechAdapter(options);
        apptech_group.setAdapter(adapter);
        adapter.setOnItemClickListener(new approvetechAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                approvetech tech=documentSnapshot.toObject(approvetech.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                String fullname=documentSnapshot.getString("fullname");
                String category=documentSnapshot.getString("category");
                String dob=documentSnapshot.getString("dob");
                Intent i = new Intent(approve_tech.this,approve_tech_details.class);
                i.putExtra("id",id);
                i.putExtra("fullname",fullname);
                i.putExtra("category",category);
                i.putExtra("dob",dob);
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
