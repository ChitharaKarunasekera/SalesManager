package com.example.salesmanagerapp.model;

import java.util.List;

public class LoginResponse {

    private List<LoginDetail> loginDetails;
    private List<LoginStatus> loginStatus;

    public List<LoginDetail> getLoginDetails() {

        return loginDetails;
    }

    public void setLoginDetails(List<LoginDetail> loginDetails) {

        this.loginDetails = loginDetails;
    }

    public List<LoginStatus> getLoginStatus() {

        return loginStatus;
    }

    public void setLoginStatus(List<LoginStatus> loginStatus) {

        this.loginStatus = loginStatus;
    }
}