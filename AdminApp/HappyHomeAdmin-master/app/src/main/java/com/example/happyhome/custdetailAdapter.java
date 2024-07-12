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


public class custdetailAdapter extends FirestoreRecyclerAdapter<custdetail,custdetailAdapter.custdetailviewholder> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private custdetailAdapter.OnItemClickListener listener;
    public custdetailAdapter(@NonNull FirestoreRecyclerOptions<custdetail> options) {super(options); }

    @Override
    protected void onBindViewHolder(@NonNull custdetailviewholder holder, int position, @NonNull custdetail custdetail) {
        holder.name.setText(custdetail.getFname());

        holder.mob.setText(custdetail.getMobile());
        holder.mail.setText(custdetail.getMail());
        holder.address.setText(custdetail.getAddress());

    }
    public custdetailviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_custdetail, parent, false);
        return new custdetailAdapter.custdetailviewholder(view);
    }


    public class custdetailviewholder extends RecyclerView.ViewHolder{
        TextView name,dob,mob,mail,address;
        public custdetailviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cname);

            mob=itemView.findViewById(R.id.cmob);
            mail=itemView.findViewById(R.id.cmail);
            address=itemView.findViewById(R.id.caddress);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(custdetailAdapter.OnItemClickListener listener) {
        this.listener=listener;
    }
}
