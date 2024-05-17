package com.example.salesmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.salesmanagerapp.api.ApiService;
import com.example.salesmanagerapp.api.RetrofitClient;
import com.example.salesmanagerapp.model.LoginResponse;
import com.example.salesmanagerapp.model.LoginStatus;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    Button callSignUp, login_btn;
    ImageView image;
    TextView loginTitle, loginSlogan;
    TextInputLayout username, password;

    private static final String BASE_URL = "http://www.axienta.lk/VantageCoreWebAPI/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // hides status bar from screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // hooks
        image = findViewById(R.id.loginImage);
        loginTitle = findViewById(R.id.loginTitle);
        loginSlogan = findViewById(R.id.loginSlogan);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.loginButton);

        // TODO: Fix Login Error (Login failed with network error: socket failed: EPERM (Operation not permitted))
        findViewById(R.id.loginButton).setOnClickListener(v -> openDashboard());


    }

    // navigate to dashboard screen
    private void openDashboard() {
        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
        startActivity(intent);
        finish();  // Close login activity
    }

    private void performLogin() {

        String user = username.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();

        Log.d(TAG, "Attempting to login with user ID: " + user);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<LoginResponse> call = apiService.loginUser(user, pass, "123", "123", "123");
        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Log.i(TAG, "Login request successful");

                    // Handle successful login
                    LoginStatus status = response.body().getLoginStatus().get(0);
                    if ("success".equalsIgnoreCase(status.getStatus())) {
                        Log.i(TAG, "Login status: " + status.getStatus() + " - navigating to Dashboard");
                        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                        startActivity(intent);
                        finish();  // Close login activity
                    } else {
                        Log.e(TAG, "Login failed with status: " + status.getStatus());
                        Toast.makeText(LoginActivity.this, "Login Failed: " + status.getStatus(), Toast.LENGTH_SHORT).show();

                        // TODO: ----------------------- MUST REMOVE -----------------------
                        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                        startActivity(intent);
                        finish();  // Close login activity
                        // TODO: ----------------------- MUST REMOVE -----------------------
                    }
                } else {
                    Log.e(TAG, "Login failed with HTTP error: " + response.message());
                    Toast.makeText(LoginActivity.this, "Login Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Log.e(TAG, "Login failed with network error: " + t.getMessage(), t);
                Toast.makeText(LoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}