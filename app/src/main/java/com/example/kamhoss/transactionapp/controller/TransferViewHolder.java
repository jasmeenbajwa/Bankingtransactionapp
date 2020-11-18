package com.example.kamhoss.transactionapp.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.kamhoss.transactionapp.R;
import com.example.kamhoss.transactionapp.model.Candidate;

/**
 * Created by melg on 3/7/18.
 */

public class TransferViewHolder extends RecyclerView.ViewHolder {

    private TextView amount;
    private String amountPassed;

    public TransferViewHolder(View itemView) {
        super(itemView);

        amount = itemView.findViewById(R.id.amount_shown);

    }



    public void onBind(Candidate candidate) {

        amountPassed = candidate.getAmount(); // getting each individual transaction in recyclerview itemView
        amount.setText("$ " + amountPassed); //sets amount to itemView

    }
}
