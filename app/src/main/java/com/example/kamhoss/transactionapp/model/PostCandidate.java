package com.example.kamhoss.transactionapp.model;

import java.io.Serializable;

/**
 * Created by melg on 3/6/18.
 */

public class PostCandidate implements Serializable{

    private String candidate;
    private String email;
    private String name;
    private int amount;
    private int user_id;


    public PostCandidate(){

    }
    public PostCandidate(String candidate, int amount, int user_id){
        this.amount = amount;
        this.candidate = candidate;
        this.user_id = user_id;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
