package com.example.ziaplex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final LoginActivity activity = LoginActivity.this;
    private NestedScrollView nestedScrollView;

    /* Layouts */
    private TextInputLayout lay_user_name;
    private TextInputLayout lay_password;

    /* Edit Text */
    private EditText et_user_name;
    private EditText et_password;

    /* Button */
    private MaterialButton btn_login;
    private MaterialTextView tv_register;


    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
        initObjects();

    }

    private void initView(){

        nestedScrollView = findViewById(R.id.nestedscrollview);

        lay_user_name = findViewById(R.id.layout_user_name);
        lay_password = findViewById(R.id.layout_password);

        et_user_name = findViewById(R.id.et_user_name);
        et_password = findViewById(R.id.et_password);

        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.tv_register);

    }

    private void initListener(){
        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    private void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                validation();
                break;
            case R.id.tv_register:
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                break;

        }
    }


    private void validation(){
        if (!inputValidation.isFilled(et_user_name, lay_user_name,getString(R.string.error_message_user_name))){
            return;
        }
        if (!inputValidation.isFilled(et_password,lay_password,getString(R.string.error_message_password))){
            return;
        }
        if(databaseHelper.checkUser(et_user_name.getText().toString().trim(),et_password.getText().toString().trim())){

            if (databaseHelper.ifAdmin(et_user_name.getText().toString().trim(),et_password.getText().toString().trim())){

                Intent intent = new Intent(activity, AdminActivity.class);
                intent.putExtra("USERNAME",et_user_name.getText().toString().trim());

            } else {
                Intent intent = new Intent(activity, UserListActivity.class);
                intent.putExtra("USERNAME",et_user_name.getText().toString().trim());
                ClearInput();
                startActivity(intent);
            }
        } else {
            Snackbar.make(nestedScrollView,getString(R.string.error_invalid_username_password), Snackbar.LENGTH_LONG).show();
        }

    }

    private void ClearInput(){
        et_user_name.setText(null);
        et_password.setText(null);
    }

}