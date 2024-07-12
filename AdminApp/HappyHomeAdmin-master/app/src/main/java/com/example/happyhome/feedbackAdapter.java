package com.example.happyhome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class feedbackAdapter extends FirestoreRecyclerAdapter<feedback2,feedbackAdapter.feedbackViewHolder>{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public feedbackAdapter(@NonNull FirestoreRecyclerOptions<feedback2> options) { super(options); }

    @Override
    protected void onBindViewHolder(@NonNull feedbackViewHolder holder, final int i, final feedback2 feedback2) {
        holder.techname.setText(feedback2.getTech_name());
        holder.custname.setText(feedback2.getC_name());
        holder.comment.setText(feedback2.getComment());
        holder.pname.setText(feedback2.getPname());
        holder.rating.setText(feedback2.getRating());

    }
    public feedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_feedback2, parent, false);

        return new feedbackViewHolder(view);
    }

    public class feedbackViewHolder extends RecyclerView.ViewHolder{
        TextView techname,custname,comment,pname,rating;
        public feedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            techname=itemView.findViewById(R.id.fbtechname);
            custname=itemView.findViewById(R.id.fbcustname);
            comment=itemView.findViewById(R.id.fbcomment);
            pname=itemView.findViewById(R.id.fbpname);
            rating=itemView.findViewById(R.id.fbrating);
        }
    }
}
