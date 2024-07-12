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

    public class tech_groupAdapter extends FirestoreRecyclerAdapter<techgroup,tech_groupAdapter.tech_groupViewHolder> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private tech_groupAdapter.OnItemClickListener listener;
    public tech_groupAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull tech_groupViewHolder holder, int position, @NonNull techgroup techgroup) {
        holder.name.setText(techgroup.getFullname());
        holder.category.setText(techgroup.getCategory());
        holder.dob.setText(techgroup.getDob());
        holder.mail.setText(techgroup.getMail());
        holder.mob.setText(techgroup.getMobile());
        holder.address.setText(techgroup.getAddress());
    }

    @NonNull
    @Override
    public tech_groupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.techgroup, parent, false);
        return new tech_groupViewHolder(view);
    }

    public class tech_groupViewHolder extends RecyclerView.ViewHolder {
        TextView name,category,dob,mail,mob,address;
        public tech_groupViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            category=itemView.findViewById(R.id.category);
            dob=itemView.findViewById(R.id.dob);
            mail=itemView.findViewById(R.id.mail);
            mob=itemView.findViewById(R.id.mob);
            address=itemView.findViewById(R.id.address);
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

    public void setOnItemClickListener(tech_groupAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
