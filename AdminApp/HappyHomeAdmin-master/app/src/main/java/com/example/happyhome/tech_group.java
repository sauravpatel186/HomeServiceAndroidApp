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

public class tech_group extends AppCompatActivity {
    RecyclerView tech_group;

    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference techref=db.collection("Technician");
    private tech_groupAdapter adapter;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_group);
        //animation code
        ConstraintLayout constraintLayout = findViewById(R.id.p13);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        //end
        mFirebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        tech_group = findViewById(R.id.apptech_group);
        tech_group.setLayoutManager(new LinearLayoutManager(this));
        Query query=techref.whereEqualTo("status","approved");
        FirestoreRecyclerOptions<techgroup> options =new FirestoreRecyclerOptions.Builder<techgroup>().setQuery(query,techgroup.class).build();
        adapter=new tech_groupAdapter(options);
        tech_group.setAdapter(adapter);
        adapter.setOnItemClickListener(new tech_groupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                techgroup tech=documentSnapshot.toObject(techgroup.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                String fullname=documentSnapshot.getString("fullname");
                String category=documentSnapshot.getString("category");
                String dob=documentSnapshot.getString("dob");
                String address=documentSnapshot.getString("address");
                String mobile=documentSnapshot.getString("mobile");
                String email=documentSnapshot.getString("mail");
                String imagename=documentSnapshot.getString("imagename");
                Intent i = new Intent(tech_group.this,tech_detail.class);
                i.putExtra("id",id);
                i.putExtra("fullname",fullname);
                i.putExtra("category",category);
                i.putExtra("dob",dob);
                i.putExtra("address",address);
                i.putExtra("mobile",mobile);
                i.putExtra("mail",email);
                i.putExtra("category",category);
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
