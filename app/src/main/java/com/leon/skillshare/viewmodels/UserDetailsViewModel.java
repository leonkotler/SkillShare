package com.leon.skillshare.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.leon.skillshare.domain.CourseDetails;
import com.leon.skillshare.domain.User;
import com.leon.skillshare.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;


public class UserDetailsViewModel extends ViewModel {
    private LiveData<User> currentUser;
    private LiveData<List<CourseDetails>> takingCoursesLiveDataList;
    private LiveData<List<CourseDetails>> offeringCoursesLiveDataList;
    private List<CourseDetails> takingCoursesSnapshotList = new ArrayList<>();
    private List<CourseDetails> offeringCoursesSnapshotList = new ArrayList<>();



    private final UserRepository userRepository;

    public UserDetailsViewModel() {
        userRepository = UserRepository.getInstance();
        currentUser = userRepository.getCurrentUser();
        offeringCoursesLiveDataList = userRepository.getOfferingCoursesList();
        takingCoursesLiveDataList = userRepository.getTakingCoursesList();
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<List<CourseDetails>> getOfferingCoursesLiveDataList() {
        return offeringCoursesLiveDataList;
    }

    public LiveData<List<CourseDetails>> getTakingCoursesLiveDataList() {
        return takingCoursesLiveDataList;
    }

    public String getCurrentUserId() {
        return userRepository.getCurrentUserId();
    }

    public String getCurrentUserEmail() {
        return userRepository.getCurrentUserEmail();
    }

    public List<CourseDetails> getTakingCoursesSnapshotList() {
        return takingCoursesSnapshotList;
    }

    public void setTakingCoursesSnapshotList(List<CourseDetails> takingCoursesSnapshotList) {
        this.takingCoursesSnapshotList = takingCoursesSnapshotList;
    }

    public List<CourseDetails> getOfferingCoursesSnapshotList() {
        return offeringCoursesSnapshotList;
    }

    public void setOfferingCoursesSnapshotList(List<CourseDetails> offeringCoursesSnapshotList) {
        this.offeringCoursesSnapshotList = offeringCoursesSnapshotList;
    }
}
