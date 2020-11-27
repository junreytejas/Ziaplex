package com.example.ziaplex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class UserListActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        textView = findViewById(R.id.textview);
        String usernameFromIntent = getIntent().getStringExtra("USERNAME");
        textView.setText(usernameFromIntent);
    }
}


