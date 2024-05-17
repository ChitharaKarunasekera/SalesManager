package com.example.salesmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import androidx.lifecycle.ViewModelProvider;


import com.example.salesmanagerapp.model.LoginViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private LoginViewModel loginViewModel;
    Button callSignUp, login_btn;
    ImageView image;
    TextView loginTitle, loginSlogan;
    TextInputLayout  username, password;

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

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_new = username.getEditText().toString();
                String password_new = password.getEditText().toString();

                // Print username and password to console
                Log.d(TAG, "This is my Username: " + username_new);
                Log.d(TAG, "Password: " + password_new);

                loginViewModel.login("SR0004", "12345678");
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult.isSuccess()) {
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();  // Close login activity
            } else {
                Toast.makeText(LoginActivity.this, "Login failed: " + loginResult.getError(), Toast.LENGTH_SHORT).show();

                // TODO: Fix Login Error
                /**
                 * Always shows says "Wrong username or password", even for correct credentials
                 * given in the spec.
                 * Credentials used:
                 * Username: SR0004
                 * Password: 12345678
                 */
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();  // Close login activity
            }
        });

        // TODO: Fix Login Error (Login failed with network error: socket failed: EPERM (Operation not permitted))
        //findViewById(R.id.loginButton).setOnClickListener(v -> openDashboard());


    }

    // navigate to dashboard screen
    private void openDashboard() {
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();  // Close login activity
    }
}