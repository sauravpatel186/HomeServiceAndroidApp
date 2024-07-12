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

public class assignwork_techdetailAdapter extends FirestoreRecyclerAdapter<techgroup,assignwork_techdetailAdapter.awtech_groupViewHolder> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private assignwork_techdetailAdapter.OnItemClickListener listener;
    public assignwork_techdetailAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull awtech_groupViewHolder holder, int position, @NonNull techgroup techgroup) {
        holder.name.setText(techgroup.getFullname());
        holder.category.setText(techgroup.getCategory());
        holder.dob.setText(techgroup.getDob());
        holder.mail.setText(techgroup.getMail());
        holder.mob.setText(techgroup.getMobile());
        holder.address.setText(techgroup.getAddress());
    }

    public awtech_groupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.techgroup, parent, false);
        return new awtech_groupViewHolder(view);
    }


    public class awtech_groupViewHolder extends RecyclerView.ViewHolder {
        TextView name,category,dob,mail,mob,address;
        public awtech_groupViewHolder(@NonNull View itemView) {
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

    public void setOnItemClickListener(assignwork_techdetailAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
