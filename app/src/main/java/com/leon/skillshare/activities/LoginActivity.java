package com.leon.skillshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.leon.skillshare.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";
    private Button registerBtn;
    private Button loginBtn;

    private EditText emailEditText;
    private EditText passwordEditText;

    private ProgressBar progressBar;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        switch (view.getId()){

            case R.id.activity_login_register_btn:
                Log.d(TAG, "onClick: Register button clicked");
                startActivity(new Intent(this,RegisterActivity.class));
                break;

            case R.id.activity_login_login_btn:
                Log.d(TAG, "onClick: Login button clicked");
                loginUser();
                break;
        }
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);
        loginBtn.setClickable(false);
        registerBtn.setClickable(false);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                loginBtn.setClickable(true);
                registerBtn.setClickable(true);

                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

