package com.example.happyhome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class approvetechAdapter extends FirestoreRecyclerAdapter<approvetech,approvetechAdapter.approvetechviewholder>{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private OnItemClickListener listener;

    public approvetechAdapter(FirestoreRecyclerOptions<approvetech> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(final approvetechviewholder holder, final int i, final approvetech approvetech) {
       holder.name.setText(approvetech.getFullname());
        holder.category.setText(approvetech.getCategory());
        holder.dob.setText(approvetech.getDob());
    }
    @NonNull
    @Override
    public approvetechviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_approvetech, parent, false);

        return new approvetechviewholder(view);
    }

    public class approvetechviewholder extends RecyclerView.ViewHolder {
        TextView name,dob,category;
        public approvetechviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.atname);
            dob=itemView.findViewById(R.id.atdob);
            category=itemView.findViewById(R.id.atcategory); itemView.setOnClickListener(new View.OnClickListener() {
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

    public void setOnItemClickListener(approvetechAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
