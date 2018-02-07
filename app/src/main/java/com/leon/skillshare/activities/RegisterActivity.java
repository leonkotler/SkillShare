package com.leon.skillshare.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leon.skillshare.R;
import com.leon.skillshare.domain.CourseDetails;
import com.leon.skillshare.domain.RegisterRequest;
import com.leon.skillshare.domain.ServerRequest;
import com.leon.skillshare.domain.User;
import com.leon.skillshare.viewmodels.AuthViewModel;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private EditText fullNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Spinner collegeSpinner;
    private Button registerBtn;

    private AuthViewModel authVm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authVm = ViewModelProviders.of(this).get(AuthViewModel.class);

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
                registerBtn.setClickable(false);
                registerUser();
                break;
        }
    }

    private void registerUser() {
        User userToRegister = new User();
        userToRegister.setEmail(emailEditText.getText().toString().trim());
        userToRegister.setFullName(fullNameEditText.getText().toString().trim());
        userToRegister.setCollege(collegeSpinner.getSelectedItem().toString());
        userToRegister.setCoursesOffering(new HashMap<String, CourseDetails>());
        userToRegister.setCoursesTaking(new HashMap<String, CourseDetails>());

        String userPassword = passwordEditText.getText().toString();

        if (userDetailsAreValid(userToRegister) && isPasswordIsValid(userPassword)) {
            submitUserDetailsToDB(userToRegister, userPassword);
        } else
            registerBtn.setClickable(true);
    }


    private boolean userDetailsAreValid(User userToRegister) {

        return (isEmailValid(userToRegister.getEmail())
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

    private void submitUserDetailsToDB(final User userToRegister, String userPassword) {

        progressBar.setVisibility(View.VISIBLE);

        RegisterRequest registerRequest = new RegisterRequest(userToRegister.getEmail(), userPassword);

        authVm.register(registerRequest).observe(this, new Observer<RegisterRequest>() {

            @Override
            public void onChanged(@Nullable RegisterRequest registerRequest) {
                if (registerRequest.isSuccessful()) {

                    userToRegister.setUserId(registerRequest.getUserId());
                    createUserWithDetails(userToRegister);

                } else if (registerRequest.getException() instanceof FirebaseAuthEmailException) {

                    emailEditText.setError("Email already exists");
                    emailEditText.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    registerBtn.setClickable(true);

                } else {

                    Toast.makeText(getApplicationContext(), registerRequest.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    registerBtn.setClickable(true);
                }
            }
        });
    }

    private void createUserWithDetails(User userToRegister) {

        authVm.createUser(userToRegister).observe(this, new Observer<ServerRequest>() {

            @Override
            public void onChanged(@Nullable ServerRequest serverRequest) {
                progressBar.setVisibility(View.GONE);
                registerBtn.setClickable(true);

                if (serverRequest.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), serverRequest.getMessage(), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), serverRequest.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

