package com.example.android_login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;

public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent myIntent = getIntent();
        String username = myIntent.getStringExtra("username");
        TextView welcome = (TextView) findViewById(R.id.welcome_txt);
        welcome.setText("Welcome user: " + username);

        // thực hiện logout
        Button logout_btn = (Button)findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(welcome.this, MainActivity.class);
                startActivity(logout);
            }
        });
    }
}