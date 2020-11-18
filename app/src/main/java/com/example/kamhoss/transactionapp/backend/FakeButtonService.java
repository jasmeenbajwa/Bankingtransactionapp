package com.example.kamhoss.transactionapp.backend;

import com.example.kamhoss.transactionapp.model.Candidate;
import com.example.kamhoss.transactionapp.model.PostCandidate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by melg on 3/6/18.
 */

public interface FakeButtonService {

    //Posting a new user to API
    @POST("user")
    Call<Candidate>createCandidate(@Body PostCandidate postCandidate);

    //Posting Money Transactions for user
    @POST("transfer")
    Call<Candidate>postTransfersCandidate(@Body PostCandidate postCandidate);

    //Getting list of users from CandidateID
    @GET("user?candidate")
    Call<List<Candidate>> getCandidateResults(@Query("candidate")String myCandidateCredential);

    //Getting list of transactions for user
    @GET("user/{id}/transfers?candidate")
    Call<List<Candidate>>getUserTranfersbyId(@Path("id")int id, @Query("candidate") String myCandidateCredential);

    // Deletes user by user id
    @DELETE("user/{id}")
    Call<Candidate>deleteUser(@Path("id")String id, @Query("candidate") String candidate);

}
