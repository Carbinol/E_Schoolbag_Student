package com.example.studentebag.User;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentebag.MainActivity;
import com.example.studentebag.R;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButtonLoginpage = (Button)findViewById(R.id.login_button_loginpage);
        loginButtonLoginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLoginData();
            }


        });
    }

    private void sendLoginData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EditText username = (EditText)findViewById(R.id.username);
                    EditText password = (EditText)findViewById(R.id.password);


                    RequestBody requestBody = new FormBody.Builder()
                            .add("username", username.getText().toString())
                            .add("password", password.getText().toString())
                            .build();
                    Request request = new Request.Builder()
                            .url("https://wenkui0229.top:18080/login")
                            .post(requestBody)
                            .build();
                    Response response = MainActivity.client.newCall(request).execute();
                    Log.d(TAG, response.code()+"");

                    String resData = response.body().string();
                    Log.d(TAG, resData);

                    showResponse(response,resData);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void showResponse(final Response response, final String responseBody) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(response.code() == 200) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("UserData", responseBody);
                    setResult(200, intent);
                    finish();
                }
                else if(response.code() == 500){
                    Toast.makeText(LoginActivity.this, "登录失败，请检查网络", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "登录失败，请检查用户名和密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
