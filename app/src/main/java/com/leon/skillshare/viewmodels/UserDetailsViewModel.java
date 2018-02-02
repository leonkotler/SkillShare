package com.leon.skillshare.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.leon.skillshare.domain.User;
import com.leon.skillshare.repositories.UserRepository;


public class UserDetailsViewModel extends ViewModel {
    private LiveData<User> currentUser;
    private final UserRepository userRepository;

    public UserDetailsViewModel() {
        userRepository = UserRepository.getInstance();
        currentUser = userRepository.getCurrentUser();
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }
    public String getCurrentUserId(){
        return userRepository.getCurrentUserId();
    }
}
