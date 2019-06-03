package com.example.studentebag.NewStudentWork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studentebag.Class.StudentWork;
import com.example.studentebag.MainActivity;
import com.example.studentebag.R;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewStudentWorkActivity extends AppCompatActivity {
    private static final String TAG = "NewStudentWorkActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student_work);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        final Long teacherWorkId = intent.getLongExtra("TeacherWorkId", 0);
        String content = intent.getStringExtra("DecContent");

        TextView showTeacherWorkTitle = (TextView)findViewById(R.id.show_teacher_work_title);
        refreshText(showTeacherWorkTitle, title);
        TextView showTeacherWorkContent = (TextView)findViewById(R.id.show_teacher_work_content);
        refreshText(showTeacherWorkContent, content);
        final EditText studentWorkContent = (EditText)findViewById(R.id.student_work_content);

        final Button commitNewStudentWork = (Button)findViewById(R.id.commit_new_student_work);
        commitNewStudentWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = studentWorkContent.getText().toString();
                StudentWork studentWork = new StudentWork(content, teacherWorkId);

                Gson gson = new Gson();
                String jsonString = gson.toJson(studentWork);

                commitStudentWork(jsonString);
                finish();
            }
        });
    }

    private void refreshText(final TextView textView, final String stringText){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(stringText);
            }
        });
    }

    private void commitStudentWork(final String json) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody requestBody = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                            .url("http://192.168.1.105:8080/students/work")
                            .post(requestBody)
                            .build();
                    Response response = MainActivity.client.newCall(request).execute();
                    Log.d(TAG, response.code()+"");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
