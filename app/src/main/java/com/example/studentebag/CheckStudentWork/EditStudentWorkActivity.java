package com.example.studentebag.CheckStudentWork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.studentebag.R;

public class EditStudentWorkActivity extends AppCompatActivity {

    private static final String TAG = "EditStudentWorkActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_work);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        String content = intent.getStringExtra("Content");
        TextView showStudentWorkTitle = (TextView)findViewById(R.id.show_student_work_title);
        TextView showStudentWorkContent = (TextView)findViewById(R.id.show_student_work_content);

        refreshText(showStudentWorkTitle, title);
        refreshText(showStudentWorkContent, content);
    }
    private void refreshText(final TextView textView, final String stringText){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(stringText);
            }
        });
    }
}
