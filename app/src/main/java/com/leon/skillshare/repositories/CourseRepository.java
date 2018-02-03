package com.leon.skillshare.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leon.skillshare.domain.Course;
import com.leon.skillshare.domain.CourseDetails;
import com.leon.skillshare.domain.ServerRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        DatabaseReference courseToAddRef = database.getReference("courses").push();
        final String courseKey = courseToAddRef.getKey();

        courseToAddRef.setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    updateUserWithOfferedCourse(course.getAuthorId(), courseKey, course);
                    postRequest.setValue(new ServerRequest(true,
                            "Course created successfully",
                            courseKey));
                } else {
                    postRequest.setValue(new ServerRequest(false,
                            task.getException().getMessage(),
                            courseKey));
                }
            }
        });

        return postRequest;
    }

    private void updateUserWithOfferedCourse(String authorId, String courseId, Course course) {
        
        DatabaseReference updateRef = database.getReference("users")
                .child(authorId)
                .child("coursesOffering");

        Map<String, Object> coursesOffering = new HashMap<>();
        coursesOffering.put(courseId,course.getName());

        updateRef.updateChildren(coursesOffering);
    }

    public LiveData<List<CourseDetails>> getAllCourses(){

        final MutableLiveData<List<CourseDetails>> courseDetailsListLiveData = new MutableLiveData<>();

        DatabaseReference coursesRef = database.getReference("courses");

        coursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<CourseDetails> courseDetailsList = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    CourseDetails cd = new CourseDetails();
                    cd.setCourseId(ds.getKey());
                    cd.setCourseName(ds.getValue(Course.class).getName());
                    courseDetailsList.add(cd);
                }

                courseDetailsListLiveData.setValue(courseDetailsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return courseDetailsListLiveData;
    }

}
