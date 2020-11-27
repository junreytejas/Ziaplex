package com.example.ziaplex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private final RegistrationActivity activity = RegistrationActivity.this;
    private NestedScrollView nestedScrollView;

    /* Layouts */
    private TextInputLayout lay_full_name;
    private TextInputLayout lay_user_name;
    private TextInputLayout lay_email;
    private TextInputLayout lay_password;
    private TextInputLayout lay_conf_password;

    /* Edit Text */
    private EditText et_full_name;
    private EditText et_user_name;
    private EditText et_email;
    private EditText et_password;
    private EditText et_conf_password;

    /* Button */
    private MaterialButton btn_register;
    private TextView tv_login;


    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();

        initView();
        initListener();
        initObjects();

    }

    private void initView(){

        nestedScrollView = findViewById(R.id.nestedscrollview);

        lay_full_name = findViewById(R.id.layout_full_name);
        lay_user_name = findViewById(R.id.layout_user_name);
        lay_email = findViewById(R.id.layout_email);
        lay_password = findViewById(R.id.layout_password);
        lay_conf_password = findViewById(R.id.layout_conf_password);

        et_full_name = findViewById(R.id.et_full_name);
        et_user_name = findViewById(R.id.et_user_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_conf_password = findViewById(R.id.et_conf_password);

        btn_register = findViewById(R.id.btn_register);
        tv_login = findViewById(R.id.tv_login);
    }

    private void initListener(){
        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    private void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                PostDataSqlite();
                break;
            case R.id.tv_login:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }
    }

    private void PostDataSqlite(){

        if (!inputValidation.isFilled(et_full_name, lay_full_name,getString(R.string.error_message_name))){
            return;
        }
        if (!inputValidation.isFilled(et_email, lay_email,getString(R.string.error_message_email))){
            return;
        }
        if (!inputValidation.isFilled(et_user_name, lay_user_name,getString(R.string.error_message_user_name))){
            return;
        }
        if (!inputValidation.isEmail(et_email, lay_email,getString(R.string.error_message_email))){
            return;
        }
        if (!inputValidation.isMatch(et_password, et_conf_password,lay_conf_password,getString(R.string.error_password_match))){
            return;
        }
        if (!databaseHelper.checkEmail(et_email.getText().toString().trim())){
            user.setFullname(et_full_name.getText().toString().trim());
            user.setUsername(et_user_name.getText().toString().trim());
            user.setEmail(et_email.getText().toString().trim());
            user.setPassword(et_password.getText().toString().trim());
            user.setType("user");
            databaseHelper.addUser(user);

            Snackbar.make(nestedScrollView,getString(R.string.success_message),Snackbar.LENGTH_LONG).show();
            ClearInput();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }else {
            Snackbar.make(nestedScrollView,getString(R.string.error_email_exists),Snackbar.LENGTH_LONG).show();
        }

    }

    private void ClearInput(){
        et_full_name.setText(null);
        et_user_name.setText(null);
        et_email.setText(null);
        et_password.setText(null);
        et_conf_password.setText(null);
    }
}