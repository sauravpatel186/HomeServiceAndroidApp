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

public class assign_work extends AppCompatActivity {
    RecyclerView aw_work;

    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference aw_workref=db.collection("Problem_Post");
    private assignwork_workdetaiAdapter adapter;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_work);
        //animation code
        ConstraintLayout constraintLayout = findViewById(R.id.p3);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        //end
        mFirebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        aw_work = findViewById(R.id.aw_work);
        aw_work.setLayoutManager(new LinearLayoutManager(this));
        Query query=aw_workref.whereEqualTo("status","n/a");
        FirestoreRecyclerOptions<assignwork_workdetail> options =new FirestoreRecyclerOptions.Builder<assignwork_workdetail>().setQuery(query,assignwork_workdetail.class).build();
        adapter=new assignwork_workdetaiAdapter(options);
        aw_work.setAdapter(adapter);
        adapter.setOnItemClickListener(new assignwork_workdetaiAdapter.OnItemClickListener() {
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                assignwork_workdetail work=documentSnapshot.toObject(assignwork_workdetail.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                String cname=documentSnapshot.getString("fname");
                String category=documentSnapshot.getString("category");
                String pname=documentSnapshot.getString("pname");
                String cmob=documentSnapshot.getString("mobile");
                Intent i = new Intent(assign_work.this,assignwork_techdetails.class);
                i.putExtra("id",id);
                i.putExtra("cfname",cname);
                i.putExtra("category",category);
                i.putExtra("pname",pname);
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
