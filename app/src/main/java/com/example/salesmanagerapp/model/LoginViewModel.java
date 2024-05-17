package com.example.salesmanagerapp.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.salesmanagerapp.repository.LoginRepository;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository = new LoginRepository();

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        loginRepository.login(username, password, new LoginRepository.LoginCallback() {
            @Override
            public void onSuccess() {

                loginResult.setValue(new LoginResult(true, null));
            }

            @Override
            public void onFailure(String error) {

                loginResult.setValue(new LoginResult(false, error));
            }
        });
    }
}