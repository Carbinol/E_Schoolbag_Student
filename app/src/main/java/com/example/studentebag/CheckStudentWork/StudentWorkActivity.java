package com.example.studentebag.CheckStudentWork;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Base64;
import android.widget.TextView;

import com.example.studentebag.Class.StudentWork;
import com.example.studentebag.Class.StudentWorkDetail;
import com.example.studentebag.Class.StudentWorkItem;
import com.example.studentebag.MainActivity;
import com.example.studentebag.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class StudentWorkActivity extends AppCompatActivity {

    private static final String TAG = "StudentWorkActivity";
    private List<StudentWorkItem> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_work);

        getStudentWorkItemList();
    }

    private void getStudentWorkItemList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("http://192.168.1.105:8080/students/work")
                            .build();
                    Response response = MainActivity.client.newCall(request).execute();
                    Log.d(TAG, response.code()+"");
                    String resData = response.body().string();
                    Log.d(TAG, resData);

                    Gson gson = new Gson();
                    List<StudentWorkDetail> studentWorkDetailList = gson.fromJson(resData, new TypeToken<List<StudentWorkDetail>>(){}.getType());

                    initStudentWorkItemList(studentWorkDetailList);
                    showResponse();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initStudentWorkItemList(List<StudentWorkDetail> studentWorkDetailList){
        for(StudentWorkDetail studentWorkDetail : studentWorkDetailList){
            itemList.add(new StudentWorkItem(studentWorkDetail.getTeacherWork().getId(), studentWorkDetail.getId(), studentWorkDetail.getTeacherWork().getTitle(),
                    new SimpleDateFormat("yyyy-MM-dd").format(studentWorkDetail.getSubmitTime()), studentWorkDetail.getContent(), studentWorkDetail.getScore(), studentWorkDetail.getRemark()));
        }
    }

    private void showResponse(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.student_work_recycler_view);
                recyclerView.addItemDecoration(new DividerItemDecoration(StudentWorkActivity.this,DividerItemDecoration.VERTICAL));
                LinearLayoutManager layoutManager = new LinearLayoutManager(StudentWorkActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                StudentWorkAdapter adapter = new StudentWorkAdapter(itemList);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
