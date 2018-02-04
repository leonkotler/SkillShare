package com.leon.skillshare.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.leon.skillshare.domain.CourseDetails;
import com.leon.skillshare.domain.User;
import com.leon.skillshare.repositories.UserRepository;

import java.util.List;


public class UserDetailsViewModel extends ViewModel {
    private LiveData<User> currentUser;
    private LiveData<List<CourseDetails>> takingCoursesList;
    private LiveData<List<CourseDetails>> offeringCoursesList;

    private final UserRepository userRepository;

    public UserDetailsViewModel() {
        userRepository = UserRepository.getInstance();
        currentUser = userRepository.getCurrentUser();
        offeringCoursesList = userRepository.getOfferingCoursesList();
        takingCoursesList = userRepository.getTakingCoursesList();
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<List<CourseDetails>> getOfferingCoursesList() {
        return offeringCoursesList;
    }

    public LiveData<List<CourseDetails>> getTakingCoursesList() {
        return takingCoursesList;
    }

    public String getCurrentUserId() {
        return userRepository.getCurrentUserId();
    }

    public String getCurrentUserEmail() {
        return userRepository.getCurrentUserEmail();
    }
}
