package com.leon.skillshare.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leon.skillshare.domain.CourseDetails;
import com.leon.skillshare.domain.User;
import com.leon.skillshare.exceptions.CurrentUserRetrievalException;

import java.util.ArrayList;
import java.util.List;


public class UserRepository {

    private static final UserRepository ourInstance = new UserRepository();

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("users");

    public static UserRepository getInstance() {
        return ourInstance;
    }

    private UserRepository() {
    }

    public LiveData<User> getCurrentUser() {

        final MutableLiveData<User> currentUserLiveData = new MutableLiveData<>();

        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        usersRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                currentUserLiveData.setValue(currentUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw new CurrentUserRetrievalException("Couldn't retrieve user with id: " + currentUserId);
            }
        });

        return currentUserLiveData;
    }

    public String getCurrentUserId() {
        return firebaseAuth.getCurrentUser().getUid();
    }

    public LiveData<List<CourseDetails>> getOfferingCoursesList() {

        final MutableLiveData<List<CourseDetails>> takingCoursesLiveDataList = new MutableLiveData<>();
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        usersRef.child(currentUserId).child("coursesOffering").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<CourseDetails> courseDetailsList = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    CourseDetails cd = new CourseDetails();
                    cd.setCourseId(ds.getKey());
                    cd.setCourseName(ds.getValue(String.class));
                    courseDetailsList.add(cd);
                }

                takingCoursesLiveDataList.setValue(courseDetailsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return takingCoursesLiveDataList;
    }
}
