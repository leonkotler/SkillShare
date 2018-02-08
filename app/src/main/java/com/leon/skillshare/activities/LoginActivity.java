package com.leon.skillshare.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.leon.skillshare.R;
import com.leon.skillshare.domain.LoginRequest;
import com.leon.skillshare.viewmodels.AuthViewModel;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registerBtn;
    private Button loginBtn;
    private EditText emailEditText;
    private EditText passwordEditText;
    private ProgressBar progressBar;

    private AuthViewModel authVm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authVm = ViewModelProviders.of(this).get(AuthViewModel.class);

        bindWidgets();
        setListeners();
    }

    private void bindWidgets() {
        registerBtn = findViewById(R.id.activity_login_register_btn);
        loginBtn = findViewById(R.id.activity_login_login_btn);
        emailEditText = findViewById(R.id.activity_login_email_input);
        passwordEditText = findViewById(R.id.activity_login_password_input);
        progressBar = findViewById(R.id.activity_login_progress_bar);
    }

    private void setListeners() {
        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.activity_login_register_btn:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.activity_login_login_btn:
                loginUser();
                break;
        }
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (inputsAreValid(email, password)) {

            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setClickable(false);
            registerBtn.setClickable(false);

            authVm.signIn(new LoginRequest(email, password)).observe(this, new Observer<LoginRequest>() {

                @Override
                public void onChanged(@Nullable LoginRequest loginRequest) {

                    progressBar.setVisibility(View.GONE);
                    loginBtn.setClickable(true);
                    registerBtn.setClickable(true);

                    if (loginRequest.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), loginRequest.getMessage(), Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), loginRequest.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private boolean inputsAreValid(String email, String password) {
        if (emailIsValid(email) && passwordIsValid(password))
            return true;
        else
            return false;
    }

    private boolean emailIsValid(String email) {
        if (email == null || email.isEmpty()) {
            emailEditText.setError("Email must not be empty");
            emailEditText.requestFocus();
            return false;
        } else
            return true;
    }

    private boolean passwordIsValid(String password) {
        if (password == null || password.isEmpty()) {
            passwordEditText.setError("Password must not be empty");
            passwordEditText.requestFocus();
            return false;
        } else
            return true;
    }
}

