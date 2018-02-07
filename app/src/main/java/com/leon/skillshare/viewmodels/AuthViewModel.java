package com.leon.skillshare.viewmodels;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.leon.skillshare.domain.LoginRequest;
import com.leon.skillshare.domain.RegisterRequest;
import com.leon.skillshare.domain.ServerRequest;
import com.leon.skillshare.domain.User;
import com.leon.skillshare.repositories.AuthRepository;
import com.leon.skillshare.repositories.UserRepository;

public class AuthViewModel extends ViewModel {
    private AuthRepository authRepository;
    private UserRepository userRepository;

    public AuthViewModel() {
        authRepository = AuthRepository.getInstance();
        userRepository = UserRepository.getInstance();
    }

    public LiveData<LoginRequest> signIn(LoginRequest loginRequest){
        return authRepository.signIn(loginRequest);
    }

    public LiveData<RegisterRequest> register(RegisterRequest registerRequest){
        return authRepository.register(registerRequest);
    }

    public LiveData<ServerRequest> createUser(User user){
        return userRepository.createUser(user);
    }
}
