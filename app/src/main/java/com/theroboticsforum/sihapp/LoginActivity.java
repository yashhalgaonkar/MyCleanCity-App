package com.theroboticsforum.sihapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //widgets
    private EditText mUsername;
    private EditText mPassword;
    private RelativeLayout mLoginButton;
    private TextView mForgetPassword;
    private CardView login_button_card_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        //find the widgets
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.login_button);
        mForgetPassword = findViewById(R.id.forgot_password_text_view);
        login_button_card_view = findViewById(R.id.login_button_card_view);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this , MainActivity.class));
                finish();
            }
        });



    }

    @Override
    public void onClick(View view) {
        //implement login funtion

    }


}
