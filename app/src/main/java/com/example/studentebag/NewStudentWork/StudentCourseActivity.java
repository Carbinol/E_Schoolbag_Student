package com.example.studentebag.NewStudentWork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.studentebag.Class.Course;
import com.example.studentebag.Class.CourseItem;
import com.example.studentebag.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;


import com.example.studentebag.R;

public class StudentCourseActivity extends AppCompatActivity {

    private static final String TAG = "StudentCourseActivity";
    private List<CourseItem> itemList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course);

        getCourseList();
    }

    private void getCourseList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("http://192.168.1.105:8080/students/courses")
                            .build();
                    Response response = MainActivity.client.newCall(request).execute();
                    Log.d(TAG, response.code() + "");
                    String resData = response.body().string();
                    Log.d(TAG, resData);

                    Gson gson = new Gson();
                    List<Course> courseList = gson.fromJson(resData, new TypeToken<List<Course>>(){}.getType());
                    for(Course course :courseList){
                        Log.d(TAG, course.getCourseId());
                        Log.d(TAG, course.getName());
                    }

                    initStudentCourseList(courseList);

                    showResponse();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.student_course_recycler_view);
                recyclerView.addItemDecoration(new DividerItemDecoration(StudentCourseActivity.this,DividerItemDecoration.VERTICAL));
                LinearLayoutManager layoutManager = new LinearLayoutManager(StudentCourseActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                StudentCourseAdapter adapter = new StudentCourseAdapter(itemList);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void initStudentCourseList(List<Course> courseList) {
        for (Course course : courseList) {
            itemList.add(new CourseItem(course.getName(), course.getCourseId()));
        }
    }
}