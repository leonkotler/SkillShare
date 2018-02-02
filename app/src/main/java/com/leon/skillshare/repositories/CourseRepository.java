package com.leon.skillshare.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leon.skillshare.domain.Course;
import com.leon.skillshare.domain.ServerRequest;


public class CourseRepository {
    private static final CourseRepository ourInstance = new CourseRepository();

    public static CourseRepository getInstance() {
        return ourInstance;
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private CourseRepository() {
    }

    public LiveData<ServerRequest> postNewCourse(final Course course) {
        final MutableLiveData<ServerRequest> postRequest = new MutableLiveData<>();

        DatabaseReference courseToAddRef = database.getReference("courses").child(course.getId());

        courseToAddRef.setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    postRequest.setValue(new ServerRequest(true,
                            "Course created successfully with id: " + course.getId(),
                            course.getId()));
                } else {
                    postRequest.setValue(new ServerRequest(false,
                            task.getException().getMessage(),
                            course.getId()));
                }
            }
        });

        return postRequest;
    }
}
