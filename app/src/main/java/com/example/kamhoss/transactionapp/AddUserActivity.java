package com.example.kamhoss.transactionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kamhoss.transactionapp.backend.FakeButtonService;
import com.example.kamhoss.transactionapp.model.Candidate;
import com.example.kamhoss.transactionapp.model.PostCandidate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by melg on 3/6/18.
 */

public class AddUserActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private Button addUserButton;
    private RecyclerView recyclerView;
    private Retrofit retrofit;

    private int statusCode;
    private String parsedName;
    private String firstNameParsed;
    private String lastNameParsed;
    private String emailAdress;
    private String TAG = " AddUSerActivity : ";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adduser_layout);

        setViews();

        // once clicked adds user to candidate's list of users
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstNameParsed = firstName.getText().toString();
                lastNameParsed = lastName.getText().toString();
                emailAdress = email.getText().toString();

                createUserRetrofit(parseName(firstNameParsed, lastNameParsed), emailAdress);

                Intent intent = new Intent(AddUserActivity.this, MainActivity.class);
                startActivity(intent);
            }


        });


    }

    public void setViews() {
        firstName = findViewById(R.id.name);
        lastName = findViewById(R.id.lastname);
        email = findViewById(R.id.password);
        addUserButton = findViewById(R.id.button);
    }

    // Capitalizes the first letter in first and last name
    public String parseName(String firstName, String lastName) {
        firstNameParsed = firstName.substring(0, 1).toUpperCase().concat(firstName.substring(1));
        lastNameParsed = lastName.substring(0, 1).toUpperCase().concat(lastName.substring(1));

        return firstNameParsed + " " + lastNameParsed;

    }

    // creates a NEW user for candidate's user List
    public int createUserRetrofit(String name, String email) {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://fake-button.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FakeButtonService fakeButtonService = retrofit.create(FakeButtonService.class);

        PostCandidate candidateCredentials = new PostCandidate();
        candidateCredentials.setCandidate("crd12");
        candidateCredentials.setEmail(email);
        candidateCredentials.setName(name);

        Call<Candidate> candidateCall = fakeButtonService.createCandidate(candidateCredentials);
        candidateCall.enqueue(new Callback<Candidate>() {
            @Override
            public void onResponse(Call<Candidate> call, Response<Candidate> response) {
                if (response.isSuccessful()) {

                    statusCode = response.code();
                    Candidate candidate = response.body();

                    Log.d(TAG, "onResponse: " + " ---->" + statusCode);
                }
            }

            @Override
            public void onFailure(Call<Candidate> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });


        return statusCode;
    }


}
