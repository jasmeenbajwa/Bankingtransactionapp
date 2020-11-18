package com.example.kamhoss.transactionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.kamhoss.transactionapp.backend.FakeButtonService;
import com.example.kamhoss.transactionapp.controller.CandidateAdapter;
import com.example.kamhoss.transactionapp.model.Candidate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private List<Candidate> resultsList = new ArrayList<>();
    private CandidateAdapter candidateAdapter;
    private int userID;
    private int statusCode;
    private String TAG = "Sending credentials:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        setCurrentUsersRecyclerView();
        getListOfUsers();






    }
    //setting the RecyclerView with the Current users created by Candidate
    public void setCurrentUsersRecyclerView(){
        recyclerView = findViewById(R.id.candidate_recycler);

        candidateAdapter = new CandidateAdapter(resultsList,getApplicationContext());
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayout = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayout);

    }
    //calling Api to grab all Current users created by Candidate
    public void getListOfUsers() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://fake-button.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FakeButtonService fakeButtonService = retrofit.create(FakeButtonService.class);
        fakeButtonService.getCandidateResults("crd12").enqueue(new Callback<List<Candidate>>() {
            @Override
            public void onResponse(Call<List<Candidate>> call, Response<List<Candidate>> response) {
                resultsList.addAll(response.body());


                for (int i = 0; i < resultsList.size(); i++) {
                    userID =  resultsList.get(i).getId();


                }

                recyclerView.setAdapter(candidateAdapter);
            }

            @Override
            public void onFailure(Call<List<Candidate>> call, Throwable t) {
                Log.d(TAG, "onFailure: " +  t.getMessage());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        candidateAdapter.notifyDataSetChanged();
        switch (item.getItemId()){
            case R.id.add_user:
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}