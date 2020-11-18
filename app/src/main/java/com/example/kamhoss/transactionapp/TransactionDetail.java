package com.example.kamhoss.transactionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamhoss.transactionapp.backend.FakeButtonService;
import com.example.kamhoss.transactionapp.controller.TransferAdapter;
import com.example.kamhoss.transactionapp.model.Candidate;
import com.example.kamhoss.transactionapp.model.PostCandidate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransactionDetail extends AppCompatActivity {

    private TextView userName;
    private Spinner recipientSpinner;
    private EditText amountSendingEditText;
    private TransferAdapter transferAdapter;
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private Button transferButton;

    private int amountSending;
    private String customerName;
    private int userID;
    private int selectedUserID;

    private List<Candidate> listOfTransactions = new ArrayList<>();
    private List<Candidate> spinnerUserList = new ArrayList<>();
    private List<String> usersList = new ArrayList<>();
    private HashMap<String, Integer> userCredentials = new HashMap<>();
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        setViews();
        getWelcomeData(); // Displays name of Current User
        setSpinnerList(customerName); // setting list of users in spinner without Current user Displayed.

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedUserID = userCredentials.get(recipientSpinner.getSelectedItem().toString());
                amountSending = Integer.parseInt(String.valueOf(amountSendingEditText.getText()));

                sendTransfer(selectedUserID, amountSending);

            }
        });

        setRecyclerView();


        getTransactionByUser(userID);





        Toast.makeText(this, String.valueOf(userID), Toast.LENGTH_SHORT).show();


    }
    public void setRecyclerView(){
        recyclerView = findViewById(R.id.trans_detailv_recy);
        transferAdapter = new TransferAdapter(listOfTransactions, getApplicationContext());
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayout = new LinearLayoutManager(TransactionDetail.this);
        recyclerView.setLayoutManager(linearLayout);
    }
    public void getWelcomeData(){
        Intent intent = getIntent();
        customerName = intent.getStringExtra("name");// Jeffrey Jorge
        userID = intent.getIntExtra("userID", 0);

        userName.setText(customerName);

    } // displays user's name once activity is opened and grabs user's corresponding id
    public void setViews(){
        userName = findViewById(R.id.detail_username);
        amountSendingEditText = findViewById(R.id.amount_sending);
        transferButton = findViewById(R.id._send_button);
        recipientSpinner = findViewById(R.id.recipient_spinner);
    }
    // gets all transactions corresponding to userID
    public void getTransactionByUser(int userId) {


        retrofit = new Retrofit.Builder()
                .baseUrl("https://fake-button.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FakeButtonService fakeButtonService = retrofit.create(FakeButtonService.class);
        fakeButtonService.getUserTranfersbyId(userId, "crd12").enqueue(new Callback<List<Candidate>>() {
            @Override
            public void onResponse(Call<List<Candidate>> call, Response<List<Candidate>> response) {
                String TAG = " getting transaction";
                listOfTransactions.clear();
                listOfTransactions.addAll(response.body());

                recyclerView.setAdapter(transferAdapter);
                Log.d(TAG, "onResponse: " + response.body());


            }

            @Override
            public void onFailure(Call<List<Candidate>> call, Throwable t) {
                String TAG = " getting transaction";
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });

    }
    // sends transfers based on selected person from spinner and amount entered in edit text
    public void sendTransfer(final int id, final int amount) {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://fake-button.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FakeButtonService fakeButtonService = retrofit.create(FakeButtonService.class);

        PostCandidate trasnferByUser = new PostCandidate("crd12", amount, id);

        Call<Candidate> transferService = fakeButtonService.postTransfersCandidate(trasnferByUser);
        transferService.enqueue(new Callback<Candidate>() {
            @Override
            public void onResponse(Call<Candidate> call, Response<Candidate> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(TransactionDetail.this, "Amount Transfered : $ " + amount + " To USER : " + id, Toast.LENGTH_SHORT).show();
                }
                String TAG = " sending transaction";

                Log.d(TAG, "Transfer service completed " + response.code());

            }

            @Override
            public void onFailure(Call<Candidate> call, Throwable t) {
                String TAG = " getting transaction";
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });


    }
    // grabs list of users from Intent and adds each name to the spinner drop down list
    public void setSpinnerList(String customerName) {
        Intent intent = getIntent();
        spinnerUserList = (ArrayList<Candidate>) intent.getSerializableExtra("SpinnerUserList");
        //getting list of user Objects

        for (Candidate name : spinnerUserList) {
            usersList.add(name.getName());// getting all user names to add to spinner List
            userCredentials.put(name.getName(), name.getId());// adding specific name with corresponding id to HashMap
        }


        if (usersList.contains(customerName)) { // checking if current user displayed is in list and remove if so.
            usersList.remove(customerName);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(TransactionDetail.this,
                android.R.layout.simple_spinner_item, usersList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        recipientSpinner.setAdapter(adapter);

    }


}
