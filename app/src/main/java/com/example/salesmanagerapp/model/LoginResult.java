package com.example.salesmanagerapp.model;

public class LoginResult {

    private boolean success;
    private String error;

    public LoginResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}