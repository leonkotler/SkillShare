package com.leon.skillshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leon.skillshare.R;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ProgressBar progressBar;

    private EditText fullNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Spinner collegeSpinner;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bindWidgets();
        setListeners();
    }

    private void bindWidgets() {
        fullNameEditText = findViewById(R.id.activity_register_fullname_input);
        emailEditText = findViewById(R.id.activity_register_email_input);
        passwordEditText = findViewById(R.id.activity_register_password_input);
        collegeSpinner = findViewById(R.id.activity_register_college_drop_down);
        registerBtn = findViewById(R.id.activity_register_register_btn);
        progressBar = findViewById(R.id.activity_register_progress_bar);
    }

    private void setListeners() {
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.activity_register_register_btn:
                Log.d(TAG, "onClick: Register button clicked");
                registerBtn.setClickable(false);
                registerUser();
                break;
        }
    }

    private void registerUser() {
        User userToRegister = new User();
        userToRegister.setEmail(emailEditText.getText().toString().trim());
        userToRegister.setFullName(fullNameEditText.getText().toString().trim());
        userToRegister.setPassword(passwordEditText.getText().toString().trim());
        userToRegister.setCollege(collegeSpinner.getSelectedItem().toString());

        if (userDetailsAreValid(userToRegister)) {
            submitUserDetailsToDB(userToRegister);
        } else
            registerBtn.setClickable(true);
    }


    private boolean userDetailsAreValid(User userToRegister) {

        return (isEmailValid(userToRegister.getEmail())
                && isPasswordIsValid(userToRegister.getPassword())
                && isFullNameValid(userToRegister.getFullName())
                && isCollegeValid(userToRegister.getCollege()));
    }

    private boolean isEmailValid(String email) {
        if (email.isEmpty()) {
            emailEditText.setError("Email must not be empty");
            emailEditText.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Email must be valid");
            emailEditText.requestFocus();
            return false;
        } else
            return true;
    }

    private boolean isPasswordIsValid(String password) {
        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters long");
            passwordEditText.requestFocus();
            return false;
        } else
            return true;
    }

    private boolean isFullNameValid(String fullName) {
        String[] fullNameArr = fullName.split(" ");

        if (fullName.length() < 2) {
            fullNameEditText.setError("Full name must contain first and last name");
            fullNameEditText.requestFocus();
            return false;
        } else if (fullNameArr[0].length() < 2 || fullNameArr[1].length() < 2) {
            fullNameEditText.setError("First and last name must be at least 2 characters long");
            fullNameEditText.requestFocus();
            return false;
        } else
            return true;
    }

    private boolean isCollegeValid(String collegeName) {
        return collegeName != null;
    }

    private void submitUserDetailsToDB(final User userToRegister) {
        Log.d(TAG, "submitUserDetailsToDB: Submitting user: " + userToRegister.toString());
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(userToRegister.getEmail(), userToRegister.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String userId = task.getResult().getUser().getUid();
                            Log.d(TAG, "User: " + userId + " has been created" );
                            createUserWithDetails(userId, userToRegister);

                        } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            emailEditText.setError("Email already exists");
                            emailEditText.requestFocus();
                            progressBar.setVisibility(View.GONE);
                            registerBtn.setClickable(true);

                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            registerBtn.setClickable(true);
                        }
                    }
                });
    }

    private void createUserWithDetails(String userId, User userToRegister) {
        userToRegister.setUserId(userId);

        DatabaseReference users = database.getReference("users").child(userId);
        users.setValue(userToRegister).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                registerBtn.setClickable(true);

                if (task.isSuccessful()) {
                    Log.d(TAG, "User details saved to DB");
                    Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}

