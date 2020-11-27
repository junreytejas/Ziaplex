package com.example.ziaplex;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class InputValidation {

    private Context context;

    InputValidation(Context context){
        this.context = context;
    }

    boolean isFilled(EditText editText, TextInputLayout textInputLayout, String message){

        String value = editText.getText().toString().trim();

        if (value.isEmpty()){
            textInputLayout.setError(message);
            hideKeyboard(editText);
            return false;
        } else textInputLayout.setErrorEnabled(false);
        return true;
    }

    boolean isEmail(EditText editText, TextInputLayout textInputLayout, String message){

        String value = editText.getText().toString().trim();

        if (value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            textInputLayout.setError(message);
            hideKeyboard(editText);
            return false;
        } else textInputLayout.setErrorEnabled(false);
        return true;
    }

    boolean isMatch(EditText editText,EditText editText2, TextInputLayout textInputLayout, String message){

        String value = editText.getText().toString().trim();
        String value2 = editText2.getText().toString().trim();

        if (!value.contentEquals(value2)){
            textInputLayout.setError(message);
            hideKeyboard(editText);
            return false;
        } else textInputLayout.setErrorEnabled(false);
        return true;
    }


    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
