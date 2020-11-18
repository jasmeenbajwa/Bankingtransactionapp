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
 * Created by melg on 3/6/18.
 */

public class CandidateAdapter extends RecyclerView.Adapter<CandidateViewHolder> {
    private List<Candidate> listOfUsers = new ArrayList<>();

    // assigns reference to list of userobjects being passed from mainActivity
    public CandidateAdapter(List<Candidate> candidates, Context context) {
        this.listOfUsers = candidates;

    }

    @Override
    public CandidateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_item_view, parent, false);
        return new CandidateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CandidateViewHolder holder, int position) {

        Candidate candidate = listOfUsers.get(position);
        /* sends each object in the userList to access each field
         and passes the list of users for spinnerList */
        holder.onBind(candidate, listOfUsers);
    }

    @Override
    public int getItemCount() {
        return listOfUsers.size();
    }
}
