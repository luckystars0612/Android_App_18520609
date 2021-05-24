package com.example.android_login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


import android.widget.EditText;
import android.widget.TextView;

import android.os.Bundle;
import com.example.android_login.SQLiteConnector;
import android.database.sqlite.SQLiteOpenHelper;
import org.w3c.dom.Text;

public class signup extends AppCompatActivity {

    SQLiteConnector sql_connect = new SQLiteConnector(this);
    //hàm kiểm tra thông tin đăng ký
    private boolean check_signup(String email, String username, String password) {
        TextView signup_stt = (TextView)findViewById(R.id.signup_stt);
        // yêu cầu nhập đủ 3 trường thông tin
        if (username.length() == 0 || password.length() == 0 || email.length() == 0) {
            signup_stt.setText("Please fill in all fields!");
            return false;
        }

        try {
            // nếu username đã tồn tại , return false
            if (sql_connect.checkUser(email)) {
                signup_stt.setText("Email has already existed!");
                return false;
            }
            //nếu username đã tồn tại, return false
            if (sql_connect.checkUser(username)) {
                signup_stt.setText("Username has already existed!");
                return false;
            }
            User user = new User();
            user.setName(username);
            user.setPassword(password);
            user.setEmail(email);
            sql_connect.addUser(user);
        } catch(Exception e) {
            signup_stt.setText("Error!!!");
            return false;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //set biến các textbox và button để dễ code
        Button signup_bn = (Button)findViewById(R.id.signup_bn);
        EditText email_txt = (EditText)findViewById(R.id.email_text);
        EditText username_txt = (EditText)findViewById(R.id.username_text);
        EditText password_txt = (EditText)findViewById(R.id.password_text);

        signup_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy data người dùng nhập vào từ các textbox
                String username = username_txt.getText().toString();
                String password = password_txt.getText().toString();
                String email = email_txt.getText().toString();
                //thông qua hàm check_signup đã định nghĩa ở trên để kiểm tra đăng ký
                //nếu hàm return true nghĩa là đăng ký thành công và chuyển sang activity signin
                // đồng thời gửi username và password , tự động điền vào 2 textbox ở phần signin
                if (check_signup(email, username, password)) {
                    Intent signup = new Intent(signup.this, MainActivity.class);
                    //2 tham số cần gửi qua signin activity, bên kia đã có intent để get data
                    signup.putExtra("username", username);
                    signup.putExtra("password", password);
                    startActivity(signup);
                }
            }
        });
    }
}