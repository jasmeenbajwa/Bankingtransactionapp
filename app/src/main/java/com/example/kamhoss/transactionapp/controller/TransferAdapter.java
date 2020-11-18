package com.example.kamhoss.transactionapp.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kamhoss.transactionapp.R;
import com.example.kamhoss.transactionapp.model.Candidate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melg on 3/7/18.
 */

public class TransferAdapter extends RecyclerView.Adapter<TransferViewHolder> {

    List<Candidate> transactions = new ArrayList<>();
    Context context;

    public TransferAdapter (List<Candidate> transactions, Context context){
        this.transactions = transactions;
        this.context = context;

    }
    @Override
    public TransferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_view, parent, false);
        return new TransferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransferViewHolder holder, int position) {
        Candidate candidate = transactions.get(position);
        holder.onBind(candidate);

    }


    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
