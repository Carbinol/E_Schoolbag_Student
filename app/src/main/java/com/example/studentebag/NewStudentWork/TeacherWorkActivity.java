package com.example.studentebag.NewStudentWork;

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

import com.example.studentebag.Class.TeacherWork;
import com.example.studentebag.Class.TeacherWorkItem;
import com.example.studentebag.Class.UserKey;
import com.example.studentebag.Cpabe;
import com.example.studentebag.MainActivity;
import com.example.studentebag.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TeacherWorkActivity extends AppCompatActivity {

    private static final String TAG = "TeacherWorkActivity";

    static String dir = "/data/user/0/com.example.studentebag/files";
    static String pubfile = dir + "/pubkey.pub";
    static String mskfile = dir + "/masterkey.msk";
    static String prvfile = dir + "/prvkey.prv";

    static String inputfile = dir + "/input";
    static String encfile = dir + "/input.cpabe";
    static String decfile = dir + "/output";
    static String attr_str = MainActivity.userDataDetail.getStudent().getAttribute();

    private List<TeacherWorkItem> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_work);

        Intent intent = getIntent();
        String courseId = intent.getStringExtra("CourseId");
        Log.d(TAG, courseId);

        getKey();

        getTeacherWorkItemList(courseId);
    }

    private void getTeacherWorkItemList(final String courseId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("http://192.168.1.105:8080/students/courses/" + courseId + "/teacherWork")
                            .build();
                    Response response = MainActivity.client.newCall(request).execute();
                    Log.d(TAG, response.code()+"");
                    String resData = response.body().string();
                    Log.d(TAG, resData);

                    Gson gson = new Gson();
                    List<TeacherWork> teacherWorkList = gson.fromJson(resData, new TypeToken<List<TeacherWork>>(){}.getType());

                    initTeacherWorkItemList(teacherWorkList);
                    showResponse();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initTeacherWorkItemList(List<TeacherWork> teacherWorkList){
        Cpabe testCpabe = new Cpabe();
        try {
            testCpabe.keygen(pubfile, prvfile, mskfile, attr_str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(TeacherWork teacherWork :teacherWorkList){
            //将密文存储为文件
            FileOutputStream outEncContent = null;
            BufferedOutputStream encContentWriter = null;
            try {
//                        "/data/user/0/com.example.cpabe/files/pubkey.pub"
                outEncContent = openFileOutput("input.cpabe", Context.MODE_PRIVATE);
                encContentWriter = new BufferedOutputStream(outEncContent);
                encContentWriter.write(Base64.decode(teacherWork.getContent(), Base64.DEFAULT));
                encContentWriter.flush();
            }
            catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    if (encContentWriter != null){
                        encContentWriter.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            //解密密文
            try{
                testCpabe.dec(pubfile, prvfile, encfile, decfile);
            }catch (NullPointerException e){
                continue;
            }catch (Exception e) {
                e.printStackTrace();
            }

            //读取得到的明文
            FileInputStream in = null;
            BufferedReader reader = null;
            StringBuilder decContentBuilder = new StringBuilder();
            try {
                in = openFileInput("output");
                reader = new BufferedReader(new InputStreamReader(in));
                String  line = "";
                while ((line = reader.readLine()) != null){
                    decContentBuilder.append(line);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            itemList.add(new TeacherWorkItem(teacherWork.getTeacherWorkId(), teacherWork.getTitle(), teacherWork.getCourseId(), decContentBuilder.toString()));
        }
    }

    private void showResponse(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.teacher_work_recycler_view);
                recyclerView.addItemDecoration(new DividerItemDecoration(TeacherWorkActivity.this,DividerItemDecoration.VERTICAL));
                LinearLayoutManager layoutManager = new LinearLayoutManager(TeacherWorkActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                TeacherWorkAdapter adapter = new TeacherWorkAdapter(itemList);
                recyclerView.setAdapter(adapter);
            }
        });
    }


    private void getKey(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("http://192.168.1.105:8080/key")
                            .build();
                    Response response = MainActivity.client.newCall(request).execute();
                    Log.d(TAG, response.code()+"");

                    String resData = response.body().string();
                    Log.d(TAG, resData);

                    Gson gson = new Gson();
                    UserKey userKey = gson.fromJson(resData, new TypeToken<UserKey>(){}.getType());
                    Log.d(TAG, userKey.getMasterKey());
                    Log.d(TAG, userKey.getPubKey());
                    FileOutputStream outPubkey = null;
                    FileOutputStream outMasterkey = null;
                    BufferedOutputStream pubkeyWriter = null;
                    BufferedOutputStream masterkeyWriter = null;
                    try {
//                        "/data/user/0/com.example.cpabe/files/pubkey.pub"
                        outPubkey = openFileOutput("pubkey.pub", Context.MODE_PRIVATE);
                        outMasterkey = openFileOutput("masterkey.msk", Context.MODE_PRIVATE);
                        pubkeyWriter = new BufferedOutputStream(outPubkey);
                        pubkeyWriter.write(Base64.decode(userKey.getPubKey(), Base64.DEFAULT));
                        pubkeyWriter.flush();
                        masterkeyWriter = new BufferedOutputStream(outMasterkey);
                        masterkeyWriter.write(Base64.decode(userKey.getMasterKey(), Base64.DEFAULT));
                        masterkeyWriter.flush();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        try {
                            if (pubkeyWriter != null){
                                pubkeyWriter.close();
                            }
                            if (masterkeyWriter != null){
                                masterkeyWriter.close();
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
