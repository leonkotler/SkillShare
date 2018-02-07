package com.leon.skillshare.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.leon.skillshare.activities.MainActivity;
import com.leon.skillshare.domain.LoginRequest;
import com.leon.skillshare.domain.RegisterRequest;


public class AuthRepository {

    private static final AuthRepository ourInstance = new AuthRepository();
    private FirebaseAuth mAuth;

    public static AuthRepository getInstance() {
        return ourInstance;
    }

    private AuthRepository() {
        mAuth = FirebaseAuth.getInstance();
    }

    public LiveData<LoginRequest> signIn(final LoginRequest loginRequest) {
        final MutableLiveData<LoginRequest> loginRequestMutableLiveData = new MutableLiveData<>();

        mAuth.signInWithEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    loginRequestMutableLiveData.setValue(new LoginRequest(true,
                            loginRequest.getEmail(),
                            loginRequest.getPassword(),
                            "Logged in successfully"));

                } else {

                    loginRequestMutableLiveData.setValue(new LoginRequest(false,
                            loginRequest.getEmail(),
                            loginRequest.getPassword(),
                            task.getException().getMessage()));
                }
            }
        });

        return loginRequestMutableLiveData;
    }

    public LiveData<RegisterRequest> register(final RegisterRequest registerRequest) {

        final MutableLiveData<RegisterRequest> registerRequestMutableLiveData = new MutableLiveData<>();

        mAuth.createUserWithEmailAndPassword(registerRequest.getEmail(), registerRequest.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    String userId = task.getResult().getUser().getUid();

                    RegisterRequest request = new RegisterRequest();
                    request.setSuccessful(true);
                    request.setUserId(userId);
                    request.setMessage("User has been created");

                    registerRequestMutableLiveData.setValue(request);

                } else {

                    RegisterRequest request = new RegisterRequest();
                    request.setException(task.getException());
                    request.setSuccessful(false);

                    registerRequestMutableLiveData.setValue(request);
                }
            }
        });

        return registerRequestMutableLiveData;
    }
}
