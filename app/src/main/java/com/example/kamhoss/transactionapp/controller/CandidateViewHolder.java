package com.example.kamhoss.transactionapp.controller;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kamhoss.transactionapp.R;
import com.example.kamhoss.transactionapp.TransactionDetail;
import com.example.kamhoss.transactionapp.model.Candidate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by melg on 3/6/18.
 */

public class CandidateViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView email;
    private TextView id;
    private Button startTransfer;
    private String userName;
    private int userId;

    public CandidateViewHolder(final View itemView) {
        super(itemView);
        setViews();
    }

    public void onBind(Candidate candidate, final List<Candidate> spinnerUserList) {

        userName = candidate.getName();
        userId = candidate.getId();

        name.setText(candidate.getName());
        email.setText(candidate.getEmail());
        id.setText("Transfer ID: " + String.valueOf(candidate.getId()));



        startTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* sends individual user ID, Name, and list of
                user object  to TransactionDetail class */
                Intent intent = new Intent(itemView.getContext(), TransactionDetail.class);
                intent.putExtra("userID",userId);
                intent.putExtra("name",userName);
                intent.putExtra("SpinnerUserList",(Serializable) spinnerUserList);
                itemView.getContext().startActivity(intent);
            }
        });

    } public void setViews(){
        name = itemView.findViewById(R.id.user_);
        email = itemView.findViewById(R.id.email);
        id = itemView.findViewById(R.id.user_id);

        startTransfer = itemView.findViewById(R.id.mainact_layout);
    }
}
