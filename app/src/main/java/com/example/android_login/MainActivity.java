package com.example.android_login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


import android.widget.EditText;
import android.widget.TextView;
import com.example.android_login.SQLiteConnector;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private EditText username_txt;
    private EditText password_txt;
    private Button signin_btn;
    private Button signup_btn;

    SQLiteConnector sql_connect = new SQLiteConnector(this);

    private boolean check_signin(String username, String password) {
        TextView status = (TextView)findViewById(R.id.status_txt);
        // yêu cầu nhập đủ username và password
        if (username.length() == 0 || password.length() == 0) {
            status.setText("Please fill in all fields!");
            return false;
        }
        try {
            // kiểm tra username và password
            if (sql_connect.checkUser(username, password) == false) {
                status.setText("Wrong username or password!");
                return false;
            }
        } catch (Exception e) {
            status.setText("Error !!! ");
            return false;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username_txt = (EditText)findViewById(R.id.username_txt);
        password_txt = (EditText)findViewById(R.id.password_txt);
        signin_btn = (Button)findViewById(R.id.signin_btn);
        signup_btn = (Button)findViewById(R.id.signup_btn);

        // data chuyển qua khi đăng ký thành công sẽ tự động về trang login và
        // điền username password vào text
        Intent myIntent = getIntent();
        // 2 trường thông tin mà ta đã gửi từ signup activity qua
        String username = myIntent.getStringExtra("username");
        String password = myIntent.getStringExtra("password");
        username_txt.setText(username);
        password_txt.setText(password);


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            //click vào nút sign up sẽ chuyển sang activity sign up
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, signup.class);
                startActivity(signup);
            }
        });

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            // click signin sẽ dùng hàm authentication để kiểm tra username và password
            public void onClick(View v) {
                String username = username_txt.getText().toString();
                String password = password_txt.getText().toString();
                // khi dùng hàm authentication kiểm tra username và password đúng
                // sẽ chuyển sang activity welcome
                if (check_signin(username, password)) {
                    Intent welcome_act = new Intent(MainActivity.this, welcome.class);
                    welcome_act.putExtra("username", username);
                    startActivity(welcome_act);
                }
            }
        });
    }
}