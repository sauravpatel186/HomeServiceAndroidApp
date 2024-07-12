package com.example.customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PaymentsAdapter extends FirestoreRecyclerAdapter<paymenthistory,PaymentsAdapter.paymenthistoryviewholder> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public PaymentsAdapter(FirestoreRecyclerOptions<paymenthistory> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull paymenthistoryviewholder paymenthistoryviewholder, int i, @NonNull paymenthistory paymenthistory) {
        paymenthistoryviewholder.name.setText("Name: "+paymenthistory.getFname());
        paymenthistoryviewholder.amount.setText("Amount: "+paymenthistory.getAmount());
        //historyviewholder.add.setText("Address: "+work.getAddress());
        paymenthistoryviewholder.date.setText("Date: "+paymenthistory.getDate());
    }

    @NonNull
    @Override
    public paymenthistoryviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_history_view, parent, false);

        return new PaymentsAdapter.paymenthistoryviewholder(view);
    }

    public class paymenthistoryviewholder extends RecyclerView.ViewHolder {
        TextView amount,date,name;
        public paymenthistoryviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.historyname);
            amount=itemView.findViewById(R.id.historyamount);
            date=itemView.findViewById(R.id.historydate);
            //add=itemView.findViewById(R.id.addresshistory);


        }
    }



}
