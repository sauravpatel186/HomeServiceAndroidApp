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
import com.example.happyhome.custdetailAdapter;

public class cust_group extends AppCompatActivity {
    RecyclerView cust_group;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    public FirebaseFirestore db= FirebaseFirestore.getInstance();
    public CollectionReference custdetailref=db.collection("Customer");
    public custdetailAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_group);
        //animation code
        ConstraintLayout constraintLayout = findViewById(R.id.p8);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        //end
        mFirebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        cust_group = findViewById(R.id.cust_group);
        cust_group.setLayoutManager(new LinearLayoutManager(this));
        Query query=custdetailref.orderBy("fname");
        FirestoreRecyclerOptions<custdetail> options =new FirestoreRecyclerOptions.Builder<custdetail>().setQuery(query,custdetail.class).build();
        adapter=new custdetailAdapter(options);
        cust_group.setAdapter(adapter);
        adapter.setOnItemClickListener(new custdetailAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                custdetail cust=documentSnapshot.toObject(custdetail.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                String fullname=documentSnapshot.getString("fname");
                String mob=documentSnapshot.getString("mobile");

                String address=documentSnapshot.getString("address");
                String mail=documentSnapshot.getString("mail");
                Intent i = new Intent(cust_group.this,cust_detail.class);
                i.putExtra("id",id);
                i.putExtra("fname",fullname);
                i.putExtra("mobile",mob);

                i.putExtra("address",address);
                i.putExtra("mail",mail);
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
