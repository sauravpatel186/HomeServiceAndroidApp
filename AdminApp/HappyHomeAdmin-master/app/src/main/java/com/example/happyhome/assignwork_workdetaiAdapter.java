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

import org.w3c.dom.Text;

public class assignwork_workdetaiAdapter extends FirestoreRecyclerAdapter<assignwork_workdetail,assignwork_workdetaiAdapter.assignwork_workdetaiViewHolder> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private assignwork_workdetaiAdapter.OnItemClickListener listener;
    public assignwork_workdetaiAdapter(@NonNull FirestoreRecyclerOptions options) { super(options);   }

    @Override
    protected void onBindViewHolder(@NonNull assignwork_workdetaiViewHolder holder, int position, @NonNull assignwork_workdetail assignwork_workdetail) {
    holder.cname.setText(assignwork_workdetail.getFname());
    holder.pname.setText(assignwork_workdetail.getPname());
    holder.category.setText(assignwork_workdetail.getCategory());
    }

    @NonNull
    @Override
    public assignwork_workdetaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_assignwork_workdetail, parent, false);
        return new assignwork_workdetaiViewHolder(view);
    }


    public class assignwork_workdetaiViewHolder extends RecyclerView.ViewHolder{
        TextView cname,pname,category;
        public assignwork_workdetaiViewHolder(@NonNull View itemView) {
            super(itemView);
            cname=itemView.findViewById(R.id.awcname);
            pname=itemView.findViewById(R.id.awprobnm);
            category=itemView.findViewById(R.id.awcategory);
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

    public void setOnItemClickListener(assignwork_workdetaiAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
