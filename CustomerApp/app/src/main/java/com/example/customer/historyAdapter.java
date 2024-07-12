package com.example.customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class historyAdapter extends FirestoreRecyclerAdapter<history,historyAdapter.historyviewholder>{
    private OnItemClickListener listener;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public historyAdapter(FirestoreRecyclerOptions<history> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(final historyviewholder historyviewholder, final int i, final history history) {
        historyviewholder.name.setText("Name: "+history.getTech_name());
        historyviewholder.problemname.setText("Problem Name: "+history.getPname());
        historyviewholder.des.setText("Description: "+history.getPdesc());
        //historyviewholder.add.setText("Address: "+work.getAddress());
        historyviewholder.date.setText("Date: "+history.getDate_Time());
    }

    @NonNull
    @Override
    public historyviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_view, parent, false);

        return new historyviewholder(view);
    }

    public class historyviewholder extends RecyclerView.ViewHolder {
        TextView name,problemname,des,add,date;



        public historyviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.custnamehistory);
            problemname=itemView.findViewById(R.id.compnamehistory);
            des=itemView.findViewById(R.id.compdetailhistory);
            //add=itemView.findViewById(R.id.addresshistory);
            date=itemView.findViewById(R.id.date);


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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
